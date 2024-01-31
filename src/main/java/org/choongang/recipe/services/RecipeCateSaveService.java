package org.choongang.recipe.services;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.recipe.controllers.RequestRecipeCategory;
import org.choongang.recipe.entities.RecipeCate;
import org.choongang.recipe.repositories.RecipeCateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeCateSaveService {

    private final RecipeCateRepository recipeCateRepository;

    public void save(RequestRecipeCategory form){
        RecipeCate recipeCate = new ModelMapper().map(form, RecipeCate.class);

        recipeCateRepository.saveAndFlush(recipeCate);

    }
}
