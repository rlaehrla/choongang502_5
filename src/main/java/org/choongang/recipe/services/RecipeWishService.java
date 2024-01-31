package org.choongang.recipe.services;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.product.entities.Product;
import org.choongang.product.entities.ProductWish;
import org.choongang.product.entities.QProductWish;
import org.choongang.recipe.controllers.RecipeDataSearch;
import org.choongang.recipe.entities.QRecipeWish;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.entities.RecipeWish;
import org.choongang.recipe.repositories.RecipeRepository;
import org.choongang.recipe.repositories.RecipeWishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class RecipeWishService {

    private final RecipeWishRepository recipeWishRepository;
    private final MemberUtil memberUtil;
    private final RecipeRepository recipeRepository;
    private final HttpServletRequest request;
    private final RecipeInfoService recipeInfoService;

    /**
     * 레시피 찜 저장
     * @param reSeq
     */
    public void save(Long reSeq){
        if(!memberUtil.isLogin()){
            throw new UnAuthorizedException("로그인이 필요한 서비스입니다.");
        }

        if(memberUtil.isFarmer()){
            throw new UnAuthorizedException(Utils.getMessage("NotFarmer", "errors"));
        }

        Member member = (Member) memberUtil.getMember();
        Recipe recipe = recipeRepository.findById(reSeq).orElseThrow(RecipeNotFoundException::new);

        RecipeWish recipeWish = RecipeWish.builder()
                .recipe(recipe)
                .member(member)
                .build();
        recipeWishRepository.saveAndFlush(recipeWish);
        System.out.println("레시피저장");
    }

    /**
     * 레시피 찜 삭제
     * @param reSeq
     */
    public void delete(Long reSeq){

        if(!memberUtil.isLogin()){
            throw new UnAuthorizedException("로그인이 필요한 서비스입니다.");
        }

        if(memberUtil.isFarmer()){
            throw new UnAuthorizedException(Utils.getMessage("NotFarmer", "errors"));
        }

        Member member = (Member) memberUtil.getMember();
        Recipe recipe = recipeRepository.findById(reSeq).orElseThrow(RecipeNotFoundException::new);

        QRecipeWish recipeWish = QRecipeWish.recipeWish;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(recipeWish.recipe.eq(recipe));
        builder.and(recipeWish.member.seq.eq(member.getSeq()));

        RecipeWish recipeWish1 = recipeWishRepository.findOne(builder).orElseThrow(RecipeWishNotFoundException::new);

        recipeWishRepository.delete(recipeWish1);
        recipeWishRepository.flush();
    }


    /**
     * 레시피 찜 했는지 확인
     * @param reSeq
     * @return
     */
    public boolean saved(Long reSeq){
        if(memberUtil.isFarmer()){
            throw new UnAuthorizedException(Utils.getMessage("NotFarmer", "errors"));
        }

        if(memberUtil.isLogin()){
            Member member = (Member) memberUtil.getMember();
            Recipe recipe = recipeRepository.findById(reSeq).orElse(null);

            if (recipe != null) {
                QRecipeWish recipeWish = QRecipeWish.recipeWish;
                BooleanBuilder builder = new BooleanBuilder();
                builder.and(recipeWish.recipe.eq(recipe));
                builder.and(recipeWish.member.seq.eq(member.getSeq()));

                return recipeWishRepository.exists(builder);
            }
        }


        return false;
    }

    public ListData<RecipeWish> getWishRecipes(RecipeDataSearch search){
        if(!memberUtil.isLogin()){
            throw new UnAuthorizedException("로그인이 필요한 서비스입니다.");
        }
        if(memberUtil.isFarmer()){
            throw new UnAuthorizedException(Utils.getMessage("NotFarmer", "errors"));
        }

        Member member = (Member) memberUtil.getMember();

        QRecipeWish recipeWish = QRecipeWish.recipeWish;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(recipeWish.member.seq.eq(member.getSeq()));

        int page = search.getPage();
        int limit = search.getLimit();

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<RecipeWish> data = recipeWishRepository.findAll(builder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<RecipeWish> items = data.getContent();

        for(RecipeWish item : items){
            Recipe recipe = item.getRecipe();
            recipeInfoService.addRecipe(recipe);

            item.setRecipe(recipe);
        }

        return new ListData<>(items, pagination);
    }
}
