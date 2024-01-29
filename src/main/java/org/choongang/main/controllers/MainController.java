package org.choongang.main.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;
import org.choongang.order.repositories.OrderItemRepository;
import org.choongang.order.service.OrderInfoService;
import org.choongang.order.service.OrderItemInfoService;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController implements ExceptionProcessor {

    private final Utils utils;
    private final ProductInfoService productInfoService;
    private final OrderItemInfoService orderItemInfoService;
    private final OrderItemRepository orderItemRepository;

    @ModelAttribute("addCss")
    public String[] addCss() {
        return new String[] { "main/style" };
    }

    @GetMapping("/")
    public String index(@ModelAttribute ProductSearch form, Model model) {


        ListData<Product> data = productInfoService.getList(form, false, true);

        model.addAttribute("items", data.getItems());
        //System.out.println(orderItemInfoService.topFarmer(1, 1));

        // 현재 월에 제철인 상품 목록 가져오기
        List<Product> inSeasonProducts = productInfoService.getInSeasonProducts();
        model.addAttribute("inSeasonProducts", inSeasonProducts);

        return utils.tpl("main/index");
    }

    @GetMapping("/search/result")
    public String search(@ModelAttribute ProductSearch form, Model model) {

        ListData<Product> data = productInfoService.getList(form, false, true);

        // 검색어가 공백인 경우
        String searchQuery = form.getName();
        if (searchQuery == null || data.getItems().isEmpty() || searchQuery.trim().isEmpty()) {
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


    @GetMapping("/question")
    public String question(Model model){

        return utils.tpl("main/question");
    }

    @GetMapping("/faq")
    public String faq(Model model) {

        commonProcess("faq", model);

        return utils.tpl("board/faq");
    }

    private void commonProcess(String mode, Model model){
        mode = StringUtils.hasText(mode) ? mode : "main";
        String pageTitle = null;

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCommonCss = new ArrayList<>();
        List<String> addCss = new ArrayList<>();



        if(mode.equals("faq")){
            pageTitle = "자주 묻는 질문";
            addCss.add("board/faq");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonCss", addCommonCss);
        model.addAttribute("addCss", addCss);
    }
}
