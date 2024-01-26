package org.choongang.recipe;

import org.choongang.commons.ListData;
import org.choongang.recipe.controllers.RecipeDataSearch;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.repositories.RecipeRepository;
import org.choongang.recipe.services.RecipeInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecipeTest {
    @Autowired
    private RecipeInfoService infoService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void test1() {
        RecipeDataSearch search = new RecipeDataSearch();
        search.setSkey("당근");
        ListData<Recipe> data = infoService.getList(search);
        data.getItems().forEach(System.out::println);
        assertEquals(1, data.getItems().size());
    }

    @Test
    void test2() {
        List<String> keywords = recipeRepository.getIngredients();
        keywords.forEach(System.out::println);

    }

    @Test
    void test3() {
        List<String> keywords = infoService.getIngredients();
        keywords.forEach(System.out::println);
    }



}
