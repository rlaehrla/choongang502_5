package org.choongang.product.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.RequestCategory;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.product.entities.Category;
import org.choongang.product.entities.Product;
import org.choongang.product.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorySaveService {
    private final CategoryRepository repository;
    private final Utils utils;

    public void save(RequestCategory form){

        Category category = new ModelMapper().map(form, Category.class);
        List<Month> months = form.getMonths().stream().map(s -> Month.valueOf(s)).toList();
        category.setMonths(months);

        repository.saveAndFlush(category);
    }

    public void saveList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("수정할 카테고리를 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            String cateCd = utils.getParam("cateCd_" + chk);

            Category category = repository.findById(cateCd).orElse(null);
            if (category == null) continue;

            String cateNm = utils.getParam("cateNm_" + chk);

            category.setCateNm(cateNm);
        }

        repository.flush();
    }

}
