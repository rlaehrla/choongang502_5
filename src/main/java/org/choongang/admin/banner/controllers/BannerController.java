package org.choongang.admin.banner.controllers;

import jakarta.validation.Valid;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.MenuDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("adminBannerController")
@RequestMapping("/admin/banner")
public class BannerController {

    @ModelAttribute("menuCode")
    public String getMenuCode() { // 주 메뉴 코드
        return "banner";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return AdminMenu.getMenus("banner");
    }

    @GetMapping
    public String list(Model model){
        commonProcess("list", model);
        return "admin/banner/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        commonProcess("add", model);


        return "admin/banner/add";
    }

    @PostMapping("/save")
    public String save(@Valid RequestBanner form, Errors errors, Model model) {



        return "redirect:/admin/banner";
    }

    private void commonProcess(String mode, Model model) {
        String pageTitle = "배너 리스트";
        mode = StringUtils.hasText(mode) ? mode : "list";

        if (mode.equals("add")) {
            pageTitle = "배너등록";
        }

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("add")) {
            // 품목 등록 또는 수정
            addCommonScript.add("ckeditor5/ckeditor");
            addCommonScript.add("fileManager");
            addScript.add("board/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}
