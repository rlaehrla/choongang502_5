package org.choongang.product.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.board.controllers.BoardDataSearch;
import org.choongang.board.entities.Board;
import org.choongang.board.entities.BoardData;
import org.choongang.board.service.BoardInfoService;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;
import org.choongang.product.constants.MainCategory;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
    private final BoardInfoService boardInfoService;
    private final BoardConfigInfoService configInfoService; // 게시판 설정 조회 서비스
    private Board board; // 게시판 설정

    /**
     * 상품 목록
     *
     * @param category   /product/fruit
     * @return
     */
    @GetMapping("/{category}")
    public String product(@PathVariable("category") MainCategory category, @ModelAttribute ProductSearch form, Model model){

        commonProcess("product", category, model);

        ListData<Product> data = productInfoService.getProducts(category, form);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("product/list");
    }

    @GetMapping("/detail/{seq}")
    public String productView(@ModelAttribute BoardDataSearch search, @PathVariable("seq") Long seq, Model model) {

        commonProcess("view", null, model);

        /* 리뷰 글 리스트 출력 S */
        String bid = "review" ;

        ListData<BoardData> data = boardInfoService.getList(bid, search);

        /* 게시판 설정 처리 */
        board = configInfoService.get(bid);
        model.addAttribute("board", board);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());
        /* 리뷰 글 리스트 출력 E */

        Product product = productInfoService.get(seq);

        model.addAttribute("product", product);

        return utils.tpl("product/view");
    }


    @GetMapping("/detail/{seq}/detail")
    public String productDetail(@PathVariable("seq") Long seq, Model model){
        Product product = productInfoService.get(seq);
        model.addAttribute("product", product) ;

        return utils.tpl("product/detail_sub/_detail");
    }

    @GetMapping("/detail/{seq}/review")
    public void productReview(@PathVariable("seq") Long seq, Model model){

    }


    @GetMapping("/detail/{seq}/exchange")
    public String productExchange(@PathVariable("seq") Long seq, Model model){

        return utils.tpl("product/detail_sub/_exchange");
    }


    private void commonProcess(String mode, MainCategory category, Model model){
        mode = StringUtils.hasText(mode) ? mode : "product";
        String pageTitle = "전체보기";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addCommonCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        if(mode.equals("product")){
            pageTitle = "전체보기";
            addCss.add("product/style");


            if(category == MainCategory.FRUIT){
                pageTitle = "과일 "+ pageTitle;
            }else if (category == MainCategory.GRAIN){
                pageTitle = "곡물 " + pageTitle;
            } else if (category == MainCategory.VEGETABLE) {
                pageTitle = "채소 " + pageTitle;
            }
        } else if (mode.equals("view")) {
            pageTitle = "상품 상세";

            addCommonScript.add("tab");
            addCommonCss.add("tab");
            addScript.add("product/detail");
            addCss.add("product/style");
        }

        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonCss", addCommonCss);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("pageTitle", pageTitle);
    }
}
