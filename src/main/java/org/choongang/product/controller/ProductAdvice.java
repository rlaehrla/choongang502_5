package org.choongang.product.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.product.entities.Category;
import org.choongang.product.service.CategoryInfoService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice("org.choongang")
@RequiredArgsConstructor
public class ProductAdvice {
    private final CategoryInfoService categoryInfoService;

    /**
     * 상품 분류는 사용자 페이지 전역에 유지 되므로 전역 속성으로 정의
     * @return
     */
    @ModelAttribute("categories")
    private List<Category> getCategories() {
        return categoryInfoService.getList();
    }
}
