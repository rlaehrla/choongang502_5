package org.choongang.farmer.management.controllers;

import org.choongang.commons.MenuDetail;
import org.choongang.farmer.management.menus.FarmerMenu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/farmer/myFarmBlog")
public class BlogEditController {

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "blog";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {

        return FarmerMenu.getMenus("blog");
    }

    /**
     * 소개글 관리
     */
    @GetMapping
    public String blogIntro(Model model) {
        commonProcess("intro", model);

        return "/admin/farmer/blog/introduction";
    }

    /**
     * 판매글 관리 뷰 페이지
     */
    @GetMapping("/salesPosts")
    public String salesPosts(Model model) {
        commonProcess("salesPosts", model);

        return "/admin/farmer/blog/salesPosts" ;
    }

    /**
     * 리뷰관리 페이지
     */
    @GetMapping("/review")
    public String reviews(Model model) {
        commonProcess("review", model);

        return "/admin/farmer/blog/review" ;
    }

    /**
     * 소식 관리 페이지
     */
    @GetMapping("/sns")
    public String sns(Model model) {
        commonProcess("sns", model);

        return "/admin/farmer/blog/sns" ;
    }

    /**
     * 공통 기능
     */
    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "intro");
        String pageTitle = "소개글관리";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("salesPosts")) {
            pageTitle = "판매글관리" ;
        } else if (mode.equals("review")) {
            pageTitle = "리뷰관리" ;
        } else if (mode.equals("sns")) {
            pageTitle = "소식관리" ;
        }

        addCommonScript.add("ckeditor5/ckeditor");
        addScript.add("board/form");

        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript) ;
        model.addAttribute("addScript", addScript) ;
    }
}
