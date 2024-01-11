package org.choongang.product.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.product.entities.Category;
import org.choongang.product.entities.QCategory;
import org.choongang.product.repositories.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class CategoryInfoService {
    private final CategoryRepository repository;
    private final MemberUtil memberUtil;


    /**
     * 분류 개별 조회
     *
     * @param cateCd
     * @return
     */
    public Category get(String cateCd) {

        Category category = (Category) repository.findById(cateCd).orElseThrow(CategoryNotFoundException::new);

        // 관리자가 아니고 미사용중인 경우 접근 불가능
        if(!memberUtil.isAdmin() && !category.isActive()){
            throw new UnAuthorizedException(Utils.getMessage("UnAuthorized", "errors"));
        }

        return category;
    }

    /**
     * 분류 목록
     *
     * @param isAll : true - 미사용, 사용 전부 목록으로 조회(관리자)
     *                  false - 사용중인 목록만 조회(프론트)
     * @return
     */
    public List<Category> getList(boolean isAll){
        QCategory category = QCategory.category;
        BooleanBuilder builder = new BooleanBuilder();

        if(!isAll){
            // 사용중인 분류만 조회
            builder.and(category.active.eq(true));
        }

        List<Category> items = (List<Category>) repository.findAll(builder, Sort.by(desc("listOrder"), desc("createdAt")));

        return items;
    }

    public List<Category> getlist(){
        // 사용중인 목룍만 조회
        return getList(false);
    }
}