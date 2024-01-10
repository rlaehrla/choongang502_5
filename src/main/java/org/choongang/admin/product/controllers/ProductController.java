package org.choongang.admin.product.controllers;

import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.MenuDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController {
    @ModelAttribute("menuCode")
    public String getMenuCode() { // 주 메뉴 코드
        return "product";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return AdminMenu.getMenus("product");
    }

    @GetMapping
    public String list(Model model){

        commonProcess("list", model);
        return "admin/product/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        commonProcess("add", model);
        return "admin/product/add";
    }
    @GetMapping("/edit")
    public String edit(Model model){
        commonProcess("edit", model);
        return "admin/product/edit";
    }

    @GetMapping("/manage")
    public String posts(Model model){
        commonProcess("manage", model);
        return "admin/product/manage";
    }



    private void commonProcess(String mode, Model model) {
        String pageTitle = "품목 리스트";
        mode = StringUtils.hasText(mode) ? mode : "list";

        if (mode.equals("add")) {
            pageTitle = "품목 등록";

        }else if(mode.equals("edit")){
            pageTitle = "품목 수정";
        }
        else if (mode.equals("posts")) {
            pageTitle = "품목 관리";
        }

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("add") || mode.equals("edit")) {
            // 품목 등록 또는 수정
            addCommonScript.add("ckeditor5/ckeditor");
            addScript.add("board/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }

}
