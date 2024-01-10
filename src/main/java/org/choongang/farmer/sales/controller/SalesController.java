package org.choongang.farmer.sales.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.MenuDetail;
import org.choongang.farmer.menus.FarmerMenu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("farmerSalesController")
@RequestMapping("/farm/manage/sales")
public class SalesController {

    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "sales";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus(){
        return FarmerMenu.getMenus("sales");
    }

    @GetMapping
    public String status(Model model){
        // 매출 현황
        commonProcess("sales", model);
        return "admin/farmer/sales/sales_status";
    }
    @GetMapping("/product")
    public String product(Model model){
        // 상품관리
        commonProcess("product", model);
        return "admin/farmer/sales/product";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "order";
        String pageTitle = "주문관리";

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
    }
}
