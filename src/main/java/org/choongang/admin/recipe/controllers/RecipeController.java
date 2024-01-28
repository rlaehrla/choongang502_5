package org.choongang.admin.recipe.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.admin.product.controllers.RequestCategory;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.MenuDetail;
import org.choongang.farmer.management.menus.FarmerMenu;
import org.choongang.product.constants.MainCategory;
import org.choongang.product.entities.Category;
import org.choongang.product.entities.Product;
import org.choongang.recipe.controllers.RecipeDataSearch;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.services.RecipeDeleteService;
import org.choongang.recipe.services.RecipeInfoService;
import org.choongang.recipe.services.RecipeSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

        ListData<Recipe> data = recipeInfoService.getList(form);
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

        // List<Category> items = categoryInfoService.getList();

        //model.addAttribute("items", items);


        return "admin/recipe/category";
    }


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
            pageTitle = "상품분류";
        }


        model.addAttribute("mainCategory", MainCategory.getList());
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
    }


}
