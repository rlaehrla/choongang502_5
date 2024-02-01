package org.choongang.recipe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.RequestBoard;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;

import org.choongang.product.entities.Category;
import org.choongang.product.entities.Product;
import org.choongang.recipe.entities.Recipe;
import org.choongang.commons.Utils;

import org.choongang.recipe.entities.RecipeCate;
import org.choongang.recipe.services.*;
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
    private final MemberUtil memberUtil;
    private final RecipeAuthService recipeAuthService;

    private Recipe recipe;

    @GetMapping
    public String recipe(@ModelAttribute RecipeDataSearch search, Model model) {
        commonProcess("recipe", model);

        /* 공식 레시피 추출 */
        List<Recipe> officialRecipe = recipeInfoService.getAdminRecipe(search).stream().limit(5).toList();

        /* 최근 레시피 5개 추출 */
        ListData<Recipe> data = recipeInfoService.getList(search);
        List<Recipe> lastRecipes = data.getItems().stream().limit(5).toList();


        model.addAttribute("officialRecipes", officialRecipe);
        model.addAttribute("lastRecipes", lastRecipes);
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

        if(!memberUtil.isLogin()){

            String script = "alert('로그인이 필요한 페이지입니다.');" +
                    "location.href='/member/login';";
            model.addAttribute("script", script);

            return "common/_execute_script";
        }


        // 관리자, 회원, 농부 체크
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
        commonProcess("edit", model);

        RequestRecipe form = recipeInfoService.getForm(seq);

        recipeAuthService.check("edit", seq);
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
            addCommonScript.add("fileManager");
            addScript.add("recipe/form");
            addScript.add("recipe/list");
            pageTitle = Utils.getMessage("레서피_작성", "commons");
        } else if (mode.equals("view")) {
            pageTitle = recipe.getRcpName();
        }else if(mode.equals("list")){
            addScript.add("recipe/list");

        }

        addCss.add("recipe/style");
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
        Recipe ingsP = recipeInfoService.getIngsP(seq);
        commonProcess(mode, model);

        model.addAttribute("recipe", recipe);
    }


}

