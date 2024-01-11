package org.choongang.product.service;

import lombok.RequiredArgsConstructor;
import org.choongang.product.entities.Category;
import org.choongang.product.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryInfoService {
    private final CategoryRepository repository;


    /**
     * 분류 개별 조회
     *
     * @param cateCd
     * @return
     */
    public Category get(String cateCd) {

        Category category = (Category) repository.findById(cateCd).orElseThrow(CategoryNotFoundException::new);

        return null;
    }
}