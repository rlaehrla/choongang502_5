package org.choongang.product.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.CategorySearch;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Farmer;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.service.MemberNotFoundException;
import org.choongang.product.constants.MainCategory;
import org.choongang.product.entities.Category;
import org.choongang.product.entities.QCategory;
import org.choongang.product.repositories.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class CategoryInfoService {
    private final CategoryRepository repository;
    private final MemberUtil memberUtil;
    private final FarmerRepository farmerRepository;


    /**
     * 분류 개별 조회
     *
     * @param cateCd
     * @return
     */
    public Category get(String cateCd) {

        Category category = (Category) repository.findById(cateCd).orElseThrow(CategoryNotFoundException::new);


        // 관리자가 아니고 미사용중인 경우 접근 불가능
        if((!memberUtil.isAdmin() && !memberUtil.isFarmer())){
            throw new UnAuthorizedException(Utils.getMessage("UnAuthorized", "errors"));
        }

        return category;
    }

    public List<Category> getList(CategorySearch search){
        QCategory category = QCategory.category;
        BooleanBuilder andBuilder = new BooleanBuilder();

        if (search != null) {
            String mainCategory = search.getMainCategory();
            List<String> cateCd = search.getCateCd();
            String sopt = search.getSopt();
            String skey = search.getSkey();

            if (StringUtils.hasText(mainCategory)) {
                andBuilder.and(category.mainCategory.eq(MainCategory.valueOf(mainCategory.trim())));
            }

            if (cateCd != null && !cateCd.isEmpty()) {
                andBuilder.and(category.cateCd.in(cateCd));
            }

            sopt = StringUtils.hasText(sopt) ? sopt : "all";

            if (StringUtils.hasText(skey)) {
                skey = skey.trim();

                BooleanExpression cond1 = category.cateCd.contains(skey);
                BooleanExpression cond2 = category.cateNm.contains(skey);

                if (sopt.equals("cateCd")) {
                    andBuilder.and(cond1);
                } else if (sopt.equals("cateNm")) {
                    andBuilder.and(cond2);

                } else {
                    BooleanBuilder orBuilder = new BooleanBuilder();
                    andBuilder.and(orBuilder.or(cond1).or(cond2));
                }
            }
        }

        return (List<Category>) repository.findAll(andBuilder, Sort.by(asc("cateCd"), desc("createdAt")));
    }

    public List<Category> getList() {
        return getList(null);
    }

}