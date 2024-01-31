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
    public void saveReci(@PathVariable("reSeq") Long reSeq){

        recipeWishService.save(reSeq);

    }

    @DeleteMapping("/saveReci/{reSeq}")
    public void delReci(@PathVariable("reSeq") Long reSeq){
        recipeWishService.delete(reSeq);
    }
}
