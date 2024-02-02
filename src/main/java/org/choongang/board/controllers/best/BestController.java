package org.choongang.board.controllers.best;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.Farmer;
import org.choongang.member.service.FarmerInfoService;
import org.choongang.product.service.ProductInfoService;
import org.choongang.recipe.controllers.RecipeDataSearch;
import org.choongang.recipe.entities.QRecipeWish;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.repositories.RecipeWishRepository;
import org.choongang.recipe.services.RecipeInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/best")
public class BestController {
    private final Utils utils;
    private final FarmerInfoService farmerInfoService;
    private final FileInfoService fileInfoService;
    private final RecipeInfoService recipeInfoService;
    private final RecipeWishRepository recipeWishRepository;
    private final ProductInfoService productInfoService;

    @GetMapping
    public String best(@ModelAttribute MemberSearch memberSearch, @ModelAttribute RecipeDataSearch recipeDataSearch, Model model){
        commonProcess("best", model);

        /* 농장 랭킹 S */
        List<Farmer> farmerData = farmerInfoService.topFarmer(memberSearch);
        List<Farmer> farmers = farmerData.stream().limit(20).toList();

        for(Farmer farmer : farmers){
            List<FileInfo> profileImage =  fileInfoService.getListDone(farmer.getGid());

            if(!profileImage.isEmpty() && profileImage != null){
                farmer.setProfileImage(profileImage.get(0));
            }
        }

        Map<Long, Long> farmerCount = new HashMap<>();
        for(Farmer farmer : farmers){
            long count = productInfoService.saleSum(farmer);
            farmerCount.put(farmer.getSeq(), count);
        }

        /* 농장 랭킹 E */


        /* 레시피 랭킹 S */

        List<Recipe> data = recipeInfoService.getBestRecipe(recipeDataSearch);
        List<Recipe> recipes = data.stream().limit(20).toList();

        Map<Recipe, Long> recipeCount = new HashMap<>();
        for(Recipe recipe : recipes){
            QRecipeWish recipeWish = QRecipeWish.recipeWish;
            long count = recipeWishRepository.count(recipeWish.recipe.eq(recipe));
            recipeCount.put(recipe, count);
        }


        /* 레시피 랭킹 E */

        model.addAttribute("farmers", farmers);
        model.addAttribute("farmerCount", farmerCount);
        model.addAttribute("recipes", recipes);
        model.addAttribute("recipeCount", recipeCount);

        return utils.tpl("board/best");
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "best";

        List<String> addCss = new ArrayList<>();

        addCss.add("board/best/best");

        model.addAttribute("addCss", addCss);
    }
}
