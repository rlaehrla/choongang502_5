package org.choongang.recipe.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.rests.JSONData;
import org.choongang.recipe.services.RecipeWishService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class ApiRecipeController {
    private final RecipeWishService recipeWishService;


    @GetMapping("/saveReci/{reSeq}")
    public JSONData<Object> saveReci(@PathVariable("reSeq") Long reSeq){

        recipeWishService.save(reSeq);

        return new JSONData<>();
    }

    @DeleteMapping("/saveReci/{reSeq}")
    public JSONData<Object> delReci(@PathVariable("reSeq") Long reSeq){
        recipeWishService.delete(reSeq);
        return new JSONData<>();
    }
}
