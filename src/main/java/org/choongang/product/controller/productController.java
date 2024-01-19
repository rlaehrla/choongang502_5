package org.choongang.product.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;

import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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
    public String product(@PathVariable("category") String category, @ModelAttribute ProductSearch form, Model model){
        ListData<Product> data = productInfoService.getList(form, true);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagenation", data.getPagination());

        return utils.tpl("product/list");
    }

    @GetMapping("/detail/{seq}")
    public String productView(@PathVariable("seq") Long seq, Model model) {
        Product product = productInfoService.get(seq);

        List<String> addCommonScript = new ArrayList<>();
        List<String> addCommonCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        addCommonScript.add("tab");
        addCommonCss.add("tab");
        addScript.add("product/detail");
        addCss.add("product/style");

        model.addAttribute("product", product);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonCss", addCommonCss);
        model.addAttribute("addCommonScript", addCommonScript);
        return utils.tpl("product/view");
    }


    @GetMapping("/detail/{seq}/detail")
    public String productDetail(@PathVariable("seq") Long seq, Model model){
        Product product = productInfoService.get(seq);
        model.addAttribute("product", product) ;

        return utils.tpl("product/detail_sub/_detail");
    }

    @GetMapping("/detail/{seq}/review")
    public String productReview(@PathVariable("seq") Long seq, Model model){

        return utils.tpl("product/detail_sub/_review");
    }


    @GetMapping("/detail/{seq}/exchange")
    public String productExchange(@PathVariable("seq") Long seq, Model model){

        return utils.tpl("product/detail_sub/_exchange");
    }
}
