package org.choongang.product.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.product.entities.Category;
import org.choongang.product.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryDeleteService {

    private final CategoryInfoService categoryInfoService;
    private final CategoryRepository repository;
    private final Utils utils;

    public void delete(String cateCd){

        Category category = categoryInfoService.get(cateCd);
        repository.delete(category);
        repository.flush();

    }

    public void deleteList(List<Integer> chks){

        for(int chk : chks){
            String cateCd = utils.getParam("cateCd_" + chk);
            delete(cateCd);
        }

    }

}
