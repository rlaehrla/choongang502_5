package org.choongang.recipe;

import lombok.RequiredArgsConstructor;
import org.choongang.recipe.entities.RecipeCate;
import org.choongang.recipe.repositories.RecipeCateRepository;
import org.choongang.recipe.services.RecipeCateInfoService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeUtils {

    private final RecipeCateRepository recipeCateRepository;

    public String findCateNm(String cateCd){
        RecipeCate recipeCate = recipeCateRepository.findById(cateCd).orElse(null);
        if(recipeCate != null){
            return recipeCate.getCateNm();
        }
        return "";
    }
}
