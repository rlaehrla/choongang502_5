package org.choongang.admin.order.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.MenuDetail;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.order.controllers.OrderSearch;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.service.OrderInfoService;
import org.choongang.order.service.OrderItemInfoService;
import org.choongang.order.service.OrderSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("adminOrderController")
@RequestMapping({"/admin/order", "/farmer/order"})
@RequiredArgsConstructor
public class OrderController implements ExceptionProcessor {

    private final OrderItemInfoService orderItemInfoService;
    private final OrderInfoService orderInfoService ;
    private final OrderSaveService orderSaveService ;
    private final MemberUtil memberUtil;
    private final HttpServletRequest request ;


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

        if(memberUtil.isFarmer()){
            ListData<OrderItem> orders = orderItemInfoService.getAll(search, memberUtil.getMember().getUserId()) ;

            model.addAttribute("orders", orders.getItems());
            model.addAttribute("pagenation", orders.getPagination());
        } else {
            ListData<OrderItem> orders = orderItemInfoService.getAll(search) ;
            model.addAttribute("orders", orders.getItems()) ;
            model.addAttribute("pagenation", orders.getPagination());
        }

        return "admin/order/list";
    }

    @GetMapping("/edit/{orderItemSeq}")
    public String orderEdit(@PathVariable("orderItemSeq") Long orderItemSeq, Model model) {

        commonProcess("edit", model);

        OrderItem item = orderItemInfoService.getOneItem(orderItemSeq) ;
        model.addAttribute("item", item) ;

        return "admin/order/edit" ;
    }

    /**
     * 주문 상태 변경 저장
     */
    @GetMapping("/save")
    public String orderStatusSave(Model model) {
        String status = request.getParameter("orderStatus") ;
        Long seq = Long.valueOf(request.getParameter("seq"));

        orderSaveService.statusSave(seq, status);

        String script = String.format("alert('%s'); location.href='/admin/order/edit/%d';",
                Utils.getMessage("주문_상태가_변경되었습니다.", "commons"), seq);
        model.addAttribute("script", script) ;

        return "common/_execute_script" ;
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
            pageTitle = "주문 설정";

        } else if (mode.equals("edit")) {
            pageTitle = "주문 정보 수정" ;
        } else if (mode.equals("setting")) {
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
