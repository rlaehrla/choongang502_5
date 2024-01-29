package org.choongang.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.cart.constants.CartType;
import org.choongang.cart.service.CartData;
import org.choongang.cart.service.CartDeleteService;
import org.choongang.cart.service.CartInfoService;
import org.choongang.cart.service.CartSaveService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController implements ExceptionProcessor {

    private final CartSaveService cartSaveService;
    private final CartInfoService cartInfoService;
    private final CartDeleteService cartDeleteService;
    private final Utils utils;

    @ModelAttribute("pageTitle")
    public String getPageTitle() {
        return Utils.getMessage("장바구니", "commons");
    }

    /**
     * 장바구니에 상품 등록
     *      mode : cart - 장바구니 페이지 노출 상품
     *             direct - 바로 구매 상품
     * @return
     */
    @PostMapping("/save")
    public String save(RequestCart form, Errors errors, Model model) {
        commonProcess("product", model);

        cartSaveService.save(form);

        String redirectURL = form.getMode().equals("DIRECT") ? "/order" : "/cart";
        String script = String.format("parent.location.replace('%s');", redirectURL);

        model.addAttribute("script", script);

        return "common/_execute_script";
    }


    /**
     * 장바구니 상품 목록
     *
     * @return
     */
    @GetMapping
    public String cart(Model model) {
        commonProcess("list", model);

        CartData cartData = cartInfoService.getCartInfo(CartType.CART);

        model.addAttribute("cartData", cartData);

        return utils.tpl("cart/list");
    }

    @PostMapping
    public String cartPs(@RequestParam("chk") List<Integer> chks,
                         @RequestParam("mode") String mode, Model model) {

        String script = "parent.location.reload()"; // 부모창 새로고침

        if (mode.equals("edit")) { // 장바구니 상품 목록 수정

            cartSaveService.saveList(chks);

        } else if (mode.equals("delete")) { // 장바구니 상품 목록 삭제

            cartDeleteService.deleteList(chks);

        } else if (mode.equals("order")) { // 장바구니 상품 주문
            cartSaveService.saveList(chks);
            String orderUrl = cartInfoService.getOrderUrl(chks);
            script = String.format("parent.location.replace('%s');", orderUrl);
        }

        model.addAttribute("script", script);
        return "common/_execute_script";
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";
        String pageTitle="장바구니";

        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        if (mode.equals("list")) { // 장바구니 상품 목록
            pageTitle = "장바구니";
            addCss.add("cart/cart");
        }

        addScript.add("cart/cart");
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }
}