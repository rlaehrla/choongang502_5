package org.choongang.admin.order;

import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
    @ModelAttribute("menuCode")
    public String getMenuCode() { // 주 메뉴 코드
        return "order";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return Menu.getMenus("order");
    }

    @GetMapping
    public String list(Model model){

        commonProcess("list", model);
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

        if (mode.equals("setting")) {
            pageTitle = "주문 설정";

        }

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
