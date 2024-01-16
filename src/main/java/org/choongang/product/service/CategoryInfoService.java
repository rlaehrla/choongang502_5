package org.choongang.product.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Farmer;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.service.MemberNotFoundException;
import org.choongang.product.entities.Category;
import org.choongang.product.entities.QCategory;
import org.choongang.product.repositories.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public List<Category> getList(){
        QCategory category = QCategory.category;

        List<Category> items = (List<Category>) repository.findAll(Sort.by(asc("cateCd"), desc("createdAt")));

        return items;
    }

}