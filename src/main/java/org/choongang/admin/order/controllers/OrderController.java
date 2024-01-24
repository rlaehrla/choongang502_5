package org.choongang.admin.order.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.MenuDetail;
import org.choongang.order.controllers.OrderSearch;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.service.OrderItemInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("adminOrderController")
@RequestMapping({"/admin/order", "/farmer/order"})
@RequiredArgsConstructor
public class OrderController implements ExceptionProcessor {

    private final OrderItemInfoService orderItemInfoService ;

    @ModelAttribute("menuCode")
    public String getMenuCode() { // 주 메뉴 코드
        return "order";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return AdminMenu.getMenus("order");
    }

    @GetMapping
    public String list(@ModelAttribute OrderSearch search, Model model){

        commonProcess("list", model);

        ListData<OrderItem> orders = orderItemInfoService.getAll(search) ;
        System.out.println(orders);
        model.addAttribute("orders", orders.getItems()) ;
        model.addAttribute("pagenation", orders.getPagination());

        return "admin/order/list";
    }
    @GetMapping("/setting")
    public String setting(Model model){
        commonProcess("setting", model);
        return "admin/order/setting";
    }

    private void commonProcess(String mode, Model model) {
        String pageTitle = "주문 리스트";
        mode = StringUtils.hasText(mode) ? mode : "list";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("setting")) {
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
