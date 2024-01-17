package org.choongang.admin.search.controllers;

import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.MenuDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("adminSearchController")
@RequestMapping("/admin/search")
public class SearchController implements ExceptionProcessor {
    @ModelAttribute("menuCode")
    public String getMenuCode() { // 주 메뉴 코드
        return "search";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return AdminMenu.getMenus("search");
    }

    @GetMapping
    public String list(Model model){

        commonProcess("list", model);
        return "admin/search/list";
    }

    private void commonProcess(String mode, Model model) {
        String pageTitle = "검색순위";
        mode = StringUtils.hasText(mode) ? mode : "list";

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
    }
}
