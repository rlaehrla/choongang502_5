package org.choongang.recipe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.RequestBoard;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.recipe.entities.Recipe;
import org.choongang.commons.Utils;

import org.choongang.recipe.services.RecipeDeleteService;
import org.choongang.recipe.services.RecipeInfoService;
import org.choongang.recipe.services.RecipeSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController implements ExceptionProcessor {
    private final Utils utils;
    private final RecipeSaveService recipeSaveService;
    private final RecipeInfoService recipeInfoService;
    private final RecipeDeleteService recipeDeleteService;

    private Recipe recipe;

    @GetMapping
    public String recipe(Model model) {
        commonProcess("recipe", model);
        return utils.tpl("recipe/recipe");
    }

    /**
     * 작성하기
     * @param form
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String write(@ModelAttribute RequestRecipe form, Model model) {
        commonProcess("add", model);
        return utils.tpl("recipe/add");
    }

    /**
     * 수정하기
     * @param seq
     * @param model
     * @return
     */
    @GetMapping("/edit/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "edit", model);
        RequestRecipe form = recipeInfoService.getForm(recipe);
        model.addAttribute("requestRecipe", form);

        return utils.tpl("recipe/edit");
    }


    /**
     * 저장하기
     * @param form
     * @param errors
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid RequestRecipe form, Errors errors, Model model) {
        String mode = form.getMode();
        commonProcess(mode, model);

        if (errors.hasErrors()) {
            return utils.tpl("recipe/" + mode);
        }
        // 레시피 저장 처리
        Recipe recipe = recipeSaveService.save(form);

        return "redirect:/recipe/list"; // 레서피 목록
    }

    /**
     * 목록 조회
     */
    @GetMapping("/list")
    public String list(@ModelAttribute RecipeDataSearch search, Model model) {
        commonProcess("list", model);
        System.out.println("search = " + search);
        ListData<Recipe> data = recipeInfoService.getList(search);
        model.addAttribute("recipes", data.getItems());
        model.addAttribute("pagination", data.getPagination());
        return utils.tpl("recipe/list");
    }

    /**
     * 상세 조회
     * @param seq
     * @param model
     * @return
     */
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq,"view", model);


        return utils.tpl("recipe/view");
    }

    /**
     * 삭제하기
     * @param seq
     * @param model
     * @return
     */
    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "delete", model);
        utils.confirmDelete();
        recipeDeleteService.delete(seq);

        return "redirect:/recipe/list";
    }



    private void commonProcess(String mode, Model model) {
        String pageTitle = Utils.getMessage("레서피", "commons");
        mode = StringUtils.hasText(mode) ? mode : "list";

        List<String> addCss = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("add") || mode.equals("edit")) {
            addCss.add("recipe/style");
            addCommonScript.add("fileManager");
            addScript.add("recipe/form");
            pageTitle = Utils.getMessage("레서피_작성", "commons");
        } else if (mode.equals("view")) {
            pageTitle = recipe.getRcpName();
    }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }

    /**
     * 공통 처리 : 상세 조회, 수정 -> 게시글 번호가 있는 경우
     *
     * @param seq : 게시글 번호
     * @param mode
     * @param model
     */

    private void commonProcess(Long seq, String mode, Model model) {
        // 글수정, 글삭제 권한 체크 필요함

        recipe = recipeInfoService.get(seq);
        System.out.println("recipe = " + recipe);
        commonProcess(mode, model);
        model.addAttribute("recipe", recipe);
    }


}

