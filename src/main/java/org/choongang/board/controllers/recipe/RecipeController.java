package org.choongang.board.controllers.recipe;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.recipe.Ingredient;
import org.choongang.board.entities.recipe.Recipe;
import org.choongang.board.repositories.recipe.IngredientRepository;
import org.choongang.board.repositories.recipe.RecipeRepository;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
레시피 모두 보기, 상세보기 test
 */
@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final Utils utils;

    @GetMapping
    public String recipe() {
        return utils.tpl("board/recipe");
    }

    @GetMapping("/recipe_all")
    public String allRcp(Model model) {
        List<Recipe> recipes = recipeRepository.findAll();
        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("recipes", recipes);
        model.addAttribute("ingredients", ingredients);

        return utils.tpl("board/recipe_all");
    }
    // 레시피 상세 보기
    @GetMapping("/{seq}")
    public String detailRcp(Model model) {

        return utils.tpl("board/recipe_detail");
    }

    @PostConstruct // 생성자 호출 -> 의존성 주입 -> @PostConstruct
    public void init() {
        ingredientRepository.save(new Ingredient("당근"));
        ingredientRepository.save(new Ingredient("오이"));
        ingredientRepository.save(new Ingredient("사과"));
        ingredientRepository.save(new Ingredient("가지"));

        recipeRepository.save(new Recipe("작성자01", "볶음밥1"));
        recipeRepository.save(new Recipe("작성자02", "볶음밥2"));
        recipeRepository.save(new Recipe("작성자03", "볶음밥3"));
        recipeRepository.save(new Recipe("작성자04", "볶음밥4"));

    }

}

