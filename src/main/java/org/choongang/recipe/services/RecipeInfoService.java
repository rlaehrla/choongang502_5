package org.choongang.recipe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.CategorySearch;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.choongang.member.constants.Authority;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Authorities;
import org.choongang.product.constants.MainCategory;
import org.choongang.product.entities.Category;
import org.choongang.product.entities.Product;
//import org.choongang.product.entities.QCategory;
import org.choongang.recipe.controllers.RecipeDataSearch;
import org.choongang.recipe.controllers.RequestRecipe;
import org.choongang.recipe.entities.QRecipe;
import org.choongang.recipe.entities.QRecipeWish;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.entities.RecipeCate;
import org.choongang.recipe.repositories.RecipeCateRepository;
import org.choongang.recipe.repositories.RecipeRepository;
import org.choongang.recipe.repositories.RecipeWishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;


@Service
@RequiredArgsConstructor
public class RecipeInfoService {

    private final EntityManager em;
    private final RecipeRepository recipeRepository;
    private final HttpServletRequest request;
    private final RecipeWishRepository recipeWishRepository;
    private final FileInfoService fileInfoService;
    private final MemberUtil memberUtil;
    private RecipeCate recipeCate;


    /**
     * 상세 조회하기
     * @param seq
     * @return
     */
    public Recipe get(Long seq) {
        Recipe recipe = recipeRepository.findById(seq).orElseThrow(RecipeNotFoundException::new);

        try {
            ObjectMapper om = new ObjectMapper();

            if (StringUtils.hasText(recipe.getRequiredIng())) {
                List<String[]> requiredIngTmp = om.readValue(recipe.getRequiredIng(), new TypeReference<>() {});

                // 필수 재료 내용
                String[] requiredIng = requiredIngTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);
                // 필수 재료 수량
                String[] requiredIngEa = requiredIngTmp.stream().map(s -> s[1])
                        .toArray(String[]::new);

                recipe.setRequiredIngP(requiredIng);
                recipe.setRequiredIngEaP(requiredIngEa);
            }
            if (StringUtils.hasText(recipe.getSubIng())) {
                List<String[]> subIngTmp = om.readValue(recipe.getSubIng(), new TypeReference<>() {});

                // 부재료 내용
                String[] subIng = subIngTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);
                String[] subIngEa = subIngTmp.stream().map(s -> s[1])
                        .toArray(String[]::new);
                recipe.setSubIngP(subIng);
                recipe.setSubIngEaP(subIngEa);
            }

            if (StringUtils.hasText(recipe.getCondiments())) {
                List<String[]> condimentsTmp = om.readValue(recipe.getCondiments(), new TypeReference<>() {});

                // 양념 내용
                String[] condiments = condimentsTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);
                // 양념 수량
                String[] condimentsEa = condimentsTmp.stream().map(s -> s[1])
                        .toArray(String[]::new);

                recipe.setCondimentsP(condiments);
                recipe.setCondimentsEaP(condimentsEa);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        addRecipe(recipe);

        // 댓글 추가 필요
 /*       List<CommentData> comments = commentInfoService.getList(seq);
        recipe.setComments(comments); // 상세보기할때만 댓글 필요*/

        return recipe;
    }



    public List<String> getIngredients(){
        List<String> keywordTmp = recipeRepository.getIngredients();

        return keywordTmp == null ? null :
                keywordTmp.stream().filter(StringUtils::hasText)
                        .flatMap(s -> Arrays.stream(s.split("__"))) // flatMap : 스트림이 여러 개 있는 경우 펼쳐서 하나로 만든다.
                        .filter(StringUtils::hasText)
                        .distinct()
                        .toList();
    }


    /**
     * 수정하기
     * Recipe 엔터티 -> RequestRecipe
     *
     * @param seq : 레시피 번호(Long)
     * @return
     */
    public RequestRecipe getForm(Long seq) {
        Recipe data = get(seq);
        RequestRecipe form = RequestRecipe.builder()
                .seq(data.getSeq())
                .gid(data.getGid())
                .rcpName(data.getRcpName())
                .rcpInfo(data.getRcpInfo())
                .estimatedT(data.getEstimatedT())
                .cateCd(data.getRecipeCate().getCateNm())
                .mainImages(data.getMainImages())
                .amount(data.getAmount())
                .mode("edit")
                .build();
        System.out.println("레시피 = " + data.getRecipeCate().getCateNm());

        try {
            ObjectMapper om = new ObjectMapper();

            if (StringUtils.hasText(data.getRequiredIng())) {
                List<String[]> requiredIngTmp = om.readValue(data.getRequiredIng(), new TypeReference<>() {});

                // 필수 재료 내용
                String[] requiredIng = requiredIngTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);
                // 필수 재료 수량
                String[] requiredIngEa = requiredIngTmp.stream().map(s -> s[1])
                        .toArray(String[]::new);

                form.setRequiredIng(requiredIng);
                form.setRequiredIngEa(requiredIngEa);
            }
            if (StringUtils.hasText(data.getSubIng())) {
                List<String[]> subIngTmp = om.readValue(data.getSubIng(), new TypeReference<>() {});

                // 부재료 내용
                String[] subIng = subIngTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);
                // 부재료 수량
                String[] subIngEa = subIngTmp.stream().map(s -> s[1])
                        .toArray(String[]::new);

                form.setSubIng(subIng);
                form.setSubIngEa(subIngEa);
            }

            if (StringUtils.hasText(data.getCondiments())) {
                List<String[]> condimentsTmp = om.readValue(data.getCondiments(), new TypeReference<>() {});

                // 양념 내용
                String[] condiments = condimentsTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);
                // 양념 수량
                String[] condimentsEa = condimentsTmp.stream().map(s -> s[1])
                        .toArray(String[]::new);

                form.setCondiments(condiments);
                form.setCondimentsEa(condimentsEa);
            }


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return form;
    }

    public Recipe getIngsP(Long seq){
        Recipe data = get(seq);

        try {
            ObjectMapper om = new ObjectMapper();

            if (StringUtils.hasText(data.getRequiredIng())) {
                List<String[]> requiredIngTmp = om.readValue(data.getRequiredIng(), new TypeReference<>() {});

                // 필수 재료 내용
                String[] requiredIng = requiredIngTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);

                data.setRequiredIngP(requiredIng);
            }
            if (StringUtils.hasText(data.getSubIng())) {
                List<String[]> subIngTmp = om.readValue(data.getSubIng(), new TypeReference<>() {});

                // 부재료 내용
                String[] subIng = subIngTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);
                data.setSubIngP(subIng);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return data;

    }


    public ListData<Recipe> getPersonalList(RecipeDataSearch search){
        QRecipe recipe = QRecipe.recipe;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(recipe.member.seq.eq(memberUtil.getMember().getSeq()));

        int page = search.getPage();
        int limit = search.getLimit();

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Recipe> data = recipeRepository.findAll(builder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Recipe> items = data.getContent();
        items.forEach(this::addRecipe);

        return new ListData<>(items, pagination);

    }

    /**
     * 목록 조회하기
     * @param search
     * @return
     */


    public ListData<Recipe> getList(RecipeDataSearch search) {
        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 10);
        int offset = (page - 1) * limit;

        QRecipe recipe = QRecipe.recipe;
        BooleanBuilder andBuilder = new BooleanBuilder();

        /* 검색 조건 처리 S */
        String cateCd = search.getCateCd();
        System.out.println("씨디 = " + cateCd);
      /*  if (StringUtils.hasText(cateCd)) {
            andBuilder.and(recipe.recipeCate.cateCd.eq(cateCd.trim()));
        }*/

        String sopt = search.getSopt(); // 옵션
        String skey = search.getSkey(); // 키워드

        sopt = StringUtils.hasText(sopt) ? sopt : "all";

        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            BooleanExpression rcpCond = recipe.rcpName.contains(skey); // 제목 - rcpName LIKE '%skey%';

            BooleanExpression nickCond = recipe.member.nickname.contains(skey);
            BooleanExpression userIdCond = recipe.member.userId.contains(skey);
            BooleanExpression rcpIngCond = recipe.keyword.contains("__" + skey + "__");

            if (sopt.equals("rcpName")) { // 제목
                andBuilder.and(rcpCond);
            } else if (sopt.equals("member")) { // 닉네임 + 아이디 (OR)
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(nickCond)
                        .or(userIdCond);
                andBuilder.and(orBuilder);
            } else if (sopt.equals("rcpIng")) { // 재료
                andBuilder.and(rcpIngCond);
            } else if (sopt.equals("all")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(nickCond)
                        .or(userIdCond)
                        .or(rcpCond)
                        .or(rcpIngCond);

                andBuilder.and(orBuilder);
            }
        }
        /* 검색 조건 처리 E */
        PathBuilder<Recipe> pathBuilder = new PathBuilder<>(Recipe.class, "recipe");
        List<Recipe> items = new JPAQueryFactory(em)
                .selectFrom(recipe)
                .leftJoin(recipe.member)
                .fetchJoin()
                .offset(offset) // 시작 번호
                .limit(limit)
                .where(andBuilder)
                .orderBy(new OrderSpecifier(Order.DESC, pathBuilder.get("createdAt")))
                // 최신게시글 순서로 정렬
                .fetch();
        System.out.println("아이템 = " + items);


        // 게시글 전체 갯수
        int total = (int) recipeRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, (int) total, 10, limit, request);

        // 이미지
        items.forEach(this::addRecipe);


        return new ListData<>(items, pagination);

    }

    public ListData<Recipe> getListAdmin(RecipeDataSearch search) {
        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 10);
        int offset = (page - 1) * limit;

        QRecipe recipe = QRecipe.recipe;
        BooleanBuilder andBuilder = new BooleanBuilder();

        String cateCd = search.getCateCd();
        List<Long> seq = search.getSeq();
        String rcpName = search.getRcpName();
        String nickname = search.getNickName();
        LocalDate sdate = search.getSdate();
        LocalDate edate = search.getEdate();

        if(StringUtils.hasText(cateCd)){
            andBuilder.and(recipe.recipeCate.cateCd.eq(cateCd));
        }

        if (seq != null && !seq.isEmpty()){
            andBuilder.and(recipe.seq.in(seq));
        }

        if(StringUtils.hasText(rcpName)){
            andBuilder.and(recipe.rcpName.contains(rcpName.trim()));
        }
        if(StringUtils.hasText(nickname)){
            andBuilder.and(recipe.member.nickname.eq(nickname));
        }

        if(sdate != null){
            andBuilder.and(recipe.createdAt.goe(LocalDateTime.of(sdate, LocalTime.of(0, 0, 0))));
        }

        if (edate != null){
            andBuilder.and(recipe.createdAt.loe(LocalDateTime.of(edate, LocalTime.of(23, 59, 59))));
        }


        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Recipe> data = recipeRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Recipe> items = data.getContent();
        items.forEach(this::addRecipe);


        return new ListData<>(items, pagination);
    }

    // 관리자 카테고리

    public List<Recipe> getList(){
        QRecipe recipe = QRecipe.recipe;
        BooleanBuilder andBuilder = new BooleanBuilder();

        return recipeRepository.findAll(Sort.by(desc("createdAt")));
    }

    /**
     * 레시피 찜순 정렬
     * @param search
     * @return
     */
    public ListData<Recipe> getBestRecipe(RecipeDataSearch search){
        QRecipe recipe = QRecipe.recipe;
        BooleanBuilder builder = new BooleanBuilder();

        int page = search.getPage();
        int limit = search.getLimit();

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("rcpLike")));

        Page<Recipe> data = recipeRepository.findAll(builder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Recipe> items = data.getContent();
        items.forEach(this::addRecipe);

        return new ListData<>(items, pagination);
    }




    /**
     * 레시피 추가처리
     * @param recipe
     */
    public void addRecipe(Recipe recipe) {
        /* 파일 정보 추가 S */
        String gid = recipe.getGid();
        String mgid = recipe.getMember().getGid();


        List<FileInfo> mainImages = fileInfoService.getListDone(gid);

        List<FileInfo> profileImages = fileInfoService.getListDone(mgid);
        if(profileImages != null && !profileImages.isEmpty()){
            recipe.setProfileImage(profileImages.get(0));
        }

        recipe.setMainImages(mainImages);

        /* 파일 정보 추가 E */

        /* 재료 가져오기 S */
        try {
            ObjectMapper om = new ObjectMapper();

            if (StringUtils.hasText(recipe.getRequiredIng())) {
                List<String[]> requiredIngTmp = om.readValue(recipe.getRequiredIng(), new TypeReference<>() {});

                // 필수 재료 내용
                String[] requiredIng = requiredIngTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);

                recipe.setRequiredIngP(requiredIng);
            }
            if (StringUtils.hasText(recipe.getSubIng())) {
                List<String[]> subIngTmp = om.readValue(recipe.getSubIng(), new TypeReference<>() {});
                // 부재료 내용
                String[] subIng = subIngTmp.stream().map(s -> s[0])
                        .toArray(String[]::new);
                recipe.setSubIngP(subIng);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        /* 재료 가져오기 E */

        /** 임시 !! */
        /* 수정, 삭제 권한 정보 처리 S */
        boolean editable = false, deletable = false, mine = false;
        AbstractMember _member = recipe.getMember(); // 작성한 회원
        boolean authoritychk = false; // 작성자가 admin인지 체크

        // 관리자 -> 삭제, 수정 모두 가능
        if (memberUtil.isAdmin()) {
            editable = true;
            deletable = true;
        }

        // 회원 -> 직접 작성한 게시글만 삭제, 수정 가능
        AbstractMember member = memberUtil.getMember();
        if (_member != null && memberUtil.isLogin() && _member.getUserId().equals(_member.getUserId())) {
            editable = true;
            deletable = true;
            mine = true;
        }

        authoritychk =
                _member.getAuthorities().stream()
                        .map(Authorities::getAuthority)
                        .anyMatch(a -> a == Authority.ADMIN || a == Authority.MANAGER);

        recipe.setEditable(editable);
        recipe.setDeletable(deletable);
        recipe.setMine(mine);
        recipe.setAuthoritychk(authoritychk);

        // 수정 버튼 노출 여부
        // 관리자 - 노출, 회원 게시글 - 직접 작성한 게시글, 비회원
        boolean showEditButton = memberUtil.isAdmin() || mine || _member == null;
        boolean showDeleteButton = showEditButton;

        recipe.setShowEditButton(showEditButton);
        recipe.setShowDeleteButton(showDeleteButton);
        /* 수정, 삭제 권한 정보 처리 E */
    }




}




