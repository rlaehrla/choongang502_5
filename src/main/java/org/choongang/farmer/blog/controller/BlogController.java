package org.choongang.farmer.blog.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.MenuDetail;
import org.choongang.commons.Utils;
import org.choongang.farmer.menus.FarmerMenu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("farmerBlogController")
@RequestMapping("/farm/manage/blog")
@RequiredArgsConstructor
public class BlogController {
    private final Utils utils;
    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "blog";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus(){
        return FarmerMenu.getMenus("blog");
    }

    @GetMapping
    public String intro(Model model){
        commonProcess("intro", model);
        return utils.tpl("farmer/blog/farmIntroduction");
    }
    @GetMapping("/sales")
    public String sales(Model model){
        commonProcess("sales", model);
        return utils.tpl("farmer/blog/farmSales");
    }
    @GetMapping("/review")
    public String review(Model model){
        commonProcess("review", model);
        return utils.tpl("farmer/blog/farmReview");
    }
    @GetMapping("/sns")
    public String sns(Model model){
        commonProcess("sns", model);
        return utils.tpl("farmer/blog/farmSns");
    }



    private void commonProcess(String mode, Model model) {
        String pageTitle = "소개설정";
        mode = StringUtils.hasText(mode) ? mode : "intro";

        if (mode.equals("sales")) {
            pageTitle = "판매글설정";
        } else if (mode.equals("review")) {
            pageTitle = "후기관리";
        } else if (mode.equals("sns")) {
            pageTitle = "소식설정";
        }

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("review")) {
            addCommonScript.add("ckeditor5/ckeditor");
            addScript.add("board/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }


}
