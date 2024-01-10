package org.choongang.farmer.order.controller;

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

@Controller("farmerOrderController")
@RequestMapping("/farm/manage/order")
@RequiredArgsConstructor
public class OrderController {
    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "order";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus(){
        return FarmerMenu.getMenus("order");
    }

    @GetMapping
    public String order(Model model){
        commonProcess("order", model);
        return "admin/farmer/order/order";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "order";
        String pageTitle = "주문관리";

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
    }
}
