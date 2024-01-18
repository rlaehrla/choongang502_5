package org.choongang.recipe.servieces;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.BoardData;
import org.choongang.board.service.BoardDataNotFoundException;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.recipe.controllers.RecipeDataSearch;
import org.choongang.recipe.entities.QRecipe;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


@Service
@RequiredArgsConstructor
public class RecipeInfoService {

    private final EntityManager em;
    private final RecipeRepository recipeRepository;
    private final HttpServletRequest request;


    /**
     * 상세 조회하기
     * @param seq
     * @return
     */
/*    public Recipe get(Long seq) {
        Recipe recipe = recipeRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);

        addRecipeData(recipe);

        return recipe;
    }*/

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
        String sopt = search.getSopt(); // 옵션
        String skey = search.getSkey(); // 키워드

        sopt = StringUtils.hasText(sopt) ? sopt.toUpperCase() : "ALL";

        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            BooleanExpression rcpCond = recipe.rcpName.contains(skey); // 제목 - rcpName LIKE '%skey%';
            //BooleanExpression contentCond = recipe.requiredIng.contains(skey); // 재료 - requiredIng LIKE '%skey%';

            if (sopt.equals("RCPNAME")) { // 제목
                andBuilder.and(rcpCond);
           /* } else if (sopt.equals("REQUIREDING")) { // 재료
                andBuilder.and(contentCond);
            } else if (sopt.equals("RCPNAME_REQUIREDING")) { // 제목 + 재료
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(rcpCond)
                        .or(contentCond);

                andBuilder.and(orBuilder);*/

            } else if (sopt.equals("MEMBER")) { // 닉네임 + 아이디 + 이름 (OR)
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(recipe.member.nickname.contains(skey))
                        .or(recipe.member.userId.contains(skey))
                        .or(recipe.member.username.contains(skey));
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

            // 게시글 전체 갯수
            long total = recipeRepository.count(andBuilder);
            Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

            return new ListData<>(items, pagination);



    }
}




