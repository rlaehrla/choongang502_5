package org.choongang.product.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class productController {
    private final Utils utils;

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
    public String productDetail(@PathVariable("seq") Long seq) {

        return utils.tpl("product/detail");
    }
}
