package org.choongang.product.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;

import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class productController {
    private final Utils utils;
    private final ProductInfoService productInfoService;

    /**
     * 상품 목록
     *
     * @param category   /product/fruit
     * @return
     */
    @GetMapping("/{category}")
    public String product(@PathVariable("category") String category){

        return utils.tpl("product/list");
    }

    @GetMapping("/detail/{seq}")
    public String productDetail(@PathVariable("seq") Long seq, Model model) {
        Product product = productInfoService.get(seq);

        model.addAttribute("product", product);

        return utils.tpl("product/detail");
    }
}
