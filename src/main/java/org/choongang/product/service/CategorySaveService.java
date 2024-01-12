package org.choongang.product.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.RequestCategory;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.product.entities.Category;
import org.choongang.product.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategorySaveService {
    private final CategoryRepository repository;
    private final MemberUtil memberUtil;

    public void save(RequestCategory form){
        // 분류코드 -> 회원아이디_분류코드
        AbstractMember member = memberUtil.getMember();
        String userId = member == null? "farmer01" : member.getUserId();
        form.setCateCd(userId + "_" + form.getCateCd());

        Category category = new ModelMapper().map(form, Category.class);

        repository.saveAndFlush(category);
    }

}
