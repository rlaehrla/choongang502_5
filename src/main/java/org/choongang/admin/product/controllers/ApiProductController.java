package org.choongang.admin.product.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionRestProcessor;
import org.choongang.commons.rests.JSONData;
import org.choongang.product.entities.Category;
import org.choongang.product.repositories.CategoryRepository;
import org.choongang.product.service.CategoryInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ApiProductController implements ExceptionRestProcessor {

    private final CategoryInfoService categoryInfoService;


    @GetMapping("/select")
    public JSONData<List<Category>> searchCategory(CategorySearch search) {
        List<Category> items = categoryInfoService.getList(search);

        return new JSONData<>(items);
    }


}
