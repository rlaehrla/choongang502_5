package org.choongang.recipe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.RequestBoard;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;

import org.choongang.product.entities.Category;
import org.choongang.recipe.entities.Recipe;
import org.choongang.commons.Utils;

import org.choongang.recipe.services.RecipeAuthService;
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

    private final RecipeAuthService recipeAuthService;
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
        // member, admin만 등록 가능
        recipeAuthService.accessCheck(form);

        return utils.tpl("recipe/add");
    }

    /**
     * 수정하기
     * @param seq
     * @param model
     * @return
     */
    @GetMapping("/edit/{seq}")
    public String edit(@PathVariable("seq") Long seq, Model model) {
        //commonProcess(seq, "edit", model);
        commonProcess("edit", model);

        RequestRecipe form = recipeInfoService.getForm(seq);

        recipeAuthService.check("edit", seq);
        model.addAttribute("requestRecipe", form);

        return utils.tpl("recipe/edit");
    }

    @GetMapping("/select")
    public String selectCate(Model model){
        //List<Category> categories = categoryInfoService.getList();

        List<String> addCss = new ArrayList<>();
        addCss.add("recipe/select");
        List<String> addJs = new ArrayList<>();
        addJs.add("recipe/select");

        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addJs);

        return "recipe/select_category";
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
        System.out.println("폼 = " + form);

        if (errors.hasErrors()) {
            return utils.tpl("recipe/" + mode);
        }
        // 레시피 저장 처리
        recipeSaveService.save(form);

        return "redirect:/recipe/list"; // 레서피 목록
    }

    /**
     * 목록 조회
     */
    @GetMapping("/list")
    public String list(@ModelAttribute RecipeDataSearch search, Model model) {
        commonProcess("list", model);

        ListData<Recipe> data = recipeInfoService.getList(search);

        List<String> ingredients = recipeInfoService.getIngredients();

        List<String> addCss = new ArrayList<>();
        addCss.add("product/style");

        model.addAttribute("addCss", addCss);
        model.addAttribute("recipes", data.getItems());
        model.addAttribute("ingredients", ingredients);
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
        addScript.add("recipe/detail");
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
        if(mode.equals("edit") || mode.equals("delete")) {
            recipeAuthService.check(mode, seq);
        }

        recipe = recipeInfoService.get(seq);
        System.out.println("레시피 = " + recipe);


        commonProcess(mode, model);

        model.addAttribute("recipe", recipe);
    }


}

