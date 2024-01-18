package org.choongang.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.cart.constants.CartType;
import org.choongang.cart.service.CartData;
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

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController implements ExceptionProcessor {

    private final CartSaveService cartSaveService;
    private final CartInfoService cartInfoService;
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

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "list";

    }
}