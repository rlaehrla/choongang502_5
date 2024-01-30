package org.choongang.admin.recipe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.admin.product.controllers.RequestCategory;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.MenuDetail;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.farmer.management.menus.FarmerMenu;
import org.choongang.product.constants.MainCategory;
import org.choongang.product.entities.Category;
import org.choongang.product.entities.Product;
import org.choongang.recipe.controllers.RecipeDataSearch;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.services.RecipeDeleteService;
import org.choongang.recipe.services.RecipeInfoService;
import org.choongang.recipe.services.RecipeSaveService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller("adminRecipeController")
@RequestMapping("/admin/recipe")
@RequiredArgsConstructor
public class RecipeController implements ExceptionProcessor {
    private final RecipeInfoService recipeInfoService;
    private final RecipeSaveService recipeSaveService;
    private final RecipeDeleteService recipeDeleteService;

/*    private final RecipeCategoryValidator recipeCategoryValidator;*/


    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "recipe";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus(){

        return AdminMenu.getMenus("recipe");
    }

    /**
     * 레시피 목록
     * @param model
     * @return
     */
    @GetMapping
    public String list(@ModelAttribute RecipeDataSearch form , Model model){

        commonProcess("list", model);

        ListData<Recipe> data = recipeInfoService.getListAdmin(form);
        //List<String> cateCd = categoryInfoService.getList().stream().map(s -> s.getCateCd()).toList();*/

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());
        //model.addAttribute("cateCd", cateCd);

        return "admin/recipe/list";
    }

    @PatchMapping
    public String editList(@RequestParam("chk") List<Integer> chks, Model model){
        commonProcess("list", model);
        recipeSaveService.saveList(chks);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    @DeleteMapping
    public String deleteList(@RequestParam("chk") List<Integer> chks, Model model){
        commonProcess("list", model);

        recipeDeleteService.deleteList(chks);
        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    /**
     * 레시피 카테고리
     * @param form
     * @param model
     * @return
     */
    @GetMapping("/category")
    public String category(@ModelAttribute RequestRecipeCategory form, Model model){
        commonProcess("category", model);

        List<Recipe> items = recipeInfoService.getList();
        model.addAttribute("items", items);


        return "admin/recipe/category";
    }
/*    @PostMapping("/category")
    public String categoryPs(@Valid RequestRecipeCategory form, Errors errors, Model model){
        commonProcess("category", model);

        recipeCategoryValidator.validate(form, errors);

        if(errors.hasErrors()){
            List<String> messages = errors.getFieldErrors().stream()
                    .map(e -> e.getCodes())
                    .map(s -> Utils.getMessage(s[0]))
                    .toList();

            throw new AlertException(messages.get(0), HttpStatus.BAD_REQUEST);
        }

        recipeSaveService.save(form);

        //분류 추가가 완료되면 부모창 새로고침
        model.addAttribute("script", "parent.location.reload()");

        return "common/_execute_script";
    }

    @PatchMapping("/category")
    public String categoryEdit(@RequestParam("chk") List<Integer> chks, Model model){
        commonProcess("category", model);

        recipeSaveService.saveList(chks);

        // 수정 완료 -> 새로고침
        model.addAttribute("script", "parent.location.reload()");

        return "common/_execute_script";
    }



    @DeleteMapping("/category")
    public String categoryDelete(@RequestParam("chk") List<Integer> chks, Model model){

        commonProcess("category", model);

        categoryDeleteService.deleteList(chks);
        // 삭제 완료 -> 새로고침
        model.addAttribute("script", "parent.location.reload()");
        return "common/_execute_script";
    }*/






    /**
     * 공통처리부분
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model){

        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "레시피목록";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("category")) {
            pageTitle = "레시피 카테고리";
        }


        model.addAttribute("mainCategory", MainCategory.getList());
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
    }


}
