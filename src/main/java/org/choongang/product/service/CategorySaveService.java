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

    public void save(RequestCategory form){

        Category category = new ModelMapper().map(form, Category.class);

        repository.saveAndFlush(category);
    }

}
