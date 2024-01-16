package org.choongang.recipe.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.recipe.entities.Ingredient;
import org.choongang.recipe.entities.Recipe;
import org.choongang.commons.Utils;
import org.choongang.recipe.repositories.IngredientRepository;
import org.choongang.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
레시피 모두 보기, 상세보기 test
 */
@Controller("recipeController")
@RequestMapping("/recipe")
@RequiredArgsConstructor
//@SessionAttributes("mType")
public class RecipeController implements ExceptionProcessor {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final Utils utils;
    private final MemberUtil memberUtil;
    private final FileUploadService uploadService;
    private String gid;


    @GetMapping
    public String recipe(Model model) {
        commonProcess("recipe", model);
        return utils.tpl("recipe/recipe");
    }
    // 레시피 모두 보기
    @GetMapping("/recipe_all")
    public String allRcp(Model model) {
        List<Recipe> recipes = recipeRepository.findAll();
        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("recipes", recipes);
        model.addAttribute("ingredients", ingredients);
        commonProcess("allRcp", model);

        return utils.tpl("recipe/recipe_all");
    }
    // 레시피 상세 보기
    @GetMapping("/{seq}")
    public String detailRcp(@PathVariable("seq") Long seq, Model model) {
        //Recipe detailRcp = recipeRepository.findById(seq);
        Optional<Recipe> detailRcp = recipeRepository.findById(seq);
        model.addAttribute("detailRcp", detailRcp);
        commonProcess("detailRcp", model);

        return utils.tpl("recipe/recipe_detail");
    }

    // 레시피 등록 폼
    @GetMapping("/write/{bid}")
    public String write(@PathVariable("bid") String bid,
                        @ModelAttribute RequestRecipe form, Model model) {
        commonProcess("add", model);
        // commonProcess(bid, "add", model);

        if (memberUtil.isLogin()) {
            AbstractMember member = memberUtil.getMember();
            form.setPoster(member.getUsername());
        }

        return utils.tpl("recipe/recipe_create");
    }

    // 레시피 등록 처리
    @PostMapping("/save")
    public String createRcp(@Valid RequestRecipe form, Errors errors, Model model) {
        //uploadService.processDone(recipe.getGid());
        String bid = form.getRid();
        String mode = form.getMode();
        //commonProcess(bid, mode, model);
        commonProcess(mode, model);


        commonProcess("createRcp", model);

        // *수정필
        //return utils.tpl("redirect:/board/recipe" + recipe.getId());
        return utils.tpl("/recipe/recipe");
    }

    @GetMapping("/rcpCate")
    public String rcpCate(@ModelAttribute("recipe") Recipe recipe, Model model) {
        //commonProcess("createRcp", model);
        // *수정필
        //return utils.tpl("redirect:/board/recipe" + recipe.getId());
        return utils.tpl("/recipe/popup/rcpCate");
    }

    // 레시피 수정하기
    @GetMapping("/{seq}/edit")
    public String editRcp(@PathVariable("seq") Long seq, Model model) {
        Optional<Recipe> editRcp = recipeRepository.findById(seq);
        model.addAttribute("editRcp", editRcp);

        return utils.tpl("board/recipe_edit");

    }

/*    // 관리자 - 레시피 관리
    @GetMapping("/category")
    private String category(Model model) {
        commonProcess("category", model);

        return utils.tpl("/recipe/admin/category");
//        return "recipe/admin/category";

    }
    // 관리자 - 레시피 재료 추가, 수정?
    @PostMapping("/category")
    private String categoryPs(Model model) {
        commonProcess("category", model);
        return "recipe/admin/category";

    }*/


    /**
     * 공통 처리 부분
     * @param mode
     * @param model
     */

    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNull(mode, "recipe");
        String pageTitle = "레시피";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("allRcp")) {
            pageTitle = "모든 레시피";
        } else if (mode.equals("detailRcp")) {
            pageTitle = "레시피 상세보기";
        } else if (mode.equals("createForm")) {
            pageTitle = "레시피 등록하기";
        } else if (mode.equals("editRcp")) {
            pageTitle = "레시피 수정하기";
        }

        addScript.add("recipe/form");
        addCommonScript.add("fileManager");
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);

    }


    @PostConstruct // 생성자 호출 -> 의존성 주입 -> @PostConstruct
    public void init() {
/*        ingredientRepository.save(new Ingredient("당근"));
        ingredientRepository.save(new Ingredient("오이"));
        ingredientRepository.save(new Ingredient("사과"));
        ingredientRepository.save(new Ingredient("가지"));

        recipeRepository.save(new Recipe("작성자01", "볶음밥1", "1"));
        recipeRepository.save(new Recipe("작성자02", "볶음밥2", "2"));
        recipeRepository.save(new Recipe("작성자03", "볶음밥3", "3"));
        recipeRepository.save(new Recipe("작성자04", "볶음밥4", "4"));*/



    }

}

