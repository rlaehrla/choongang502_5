package org.choongang.main.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class MainController implements ExceptionProcessor {

    private final Utils utils;
    private final ProductInfoService productInfoService;

    @ModelAttribute("addCss")
    public String[] addCss() {
        return new String[] { "main/style" };
    }

    @GetMapping("/")
    public String index() {

        return utils.tpl("main/index");
    }

    @GetMapping("/search/result")
    public String search(@ModelAttribute ProductSearch form, Model model) {

        ListData<Product> data = productInfoService.getList(form, true);

        // 검색어가 공백인 경우
        String searchQuery = form.getName();
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            model.addAttribute("noResultsMessage", "조회된 상품이 없습니다.");
            return utils.tpl("search/result");
        }

        model.addAttribute("items", data.getItems());

        return utils.tpl("search/result");
    }

    @GetMapping("/policy/terms_of_service") // 서비스 이용약관 이동
    public String service() {
        return utils.tpl("outlines/terms_of_service");
    }

    @GetMapping("/policy/privacy") // 개인정보 처리방침 이동
    public String privacy() {
        return utils.tpl("outlines/privacy");
    }
}
