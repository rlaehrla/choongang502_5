package org.choongang.member.controllers;


import groovyjarjarantlr4.v4.gui.JFileChooserConfirmOverwrite;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.Point;
import org.choongang.member.service.PointInfoService;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.order.service.OrderInfoService;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController { //implements ExceptionProcessor {
    private final Utils utils;
    private final FileInfoService fileInfoService;
    private final OrderInfoService orderInfoService;
    private final PointInfoService pointInfoService;
    private final ProductInfoService productInfoService;

    @GetMapping
    public String myPage(Model model) {
        commonProcess("myPage", model);

        ListData<OrderInfo> orderInfos = orderInfoService.getList(3);
        List<OrderInfo> orders = orderInfos.getItems().stream().limit(5).toList();

        for(OrderInfo order : orders){
            List<OrderItem> items = order.getOrderItems();
            for(OrderItem item : items){
                Product product = productInfoService.get(item.getProduct().getSeq());
                item.setProduct(product);
            }
        }

        model.addAttribute("point", pointInfoService.pointSum());
        model.addAttribute("orders", orders);
        return utils.tpl("member/mypage/mypage");
    }


    /**
     * 마이 다이어리
     * @param model
     * @return
     */
    @GetMapping("/diary")
    public String diary(@ModelAttribute RequestMemberInfo memberInfo, Model model, Errors errors) {
        commonProcess("diary", model);
        return utils.tpl("member/mypage/diary");
    }

    @GetMapping("/diary/content/{tab}")
    public String content(@PathVariable("tab") String tab) {
        return utils.tpl("member/mypage/content/" + tab);
    }


    /**
     * 전체 주문내역
     * @param model
     * @return
     */
    @GetMapping("/orders")
    public String orders(Model model) {
        commonProcess("orders", model);

        ListData<OrderInfo> orderInfos = orderInfoService.getList();
        List<OrderInfo> orders = orderInfos.getItems();
        Pagination pagination = orderInfos.getPagination();

        model.addAttribute("orders", orders);
        model.addAttribute("pagination", pagination);
        return utils.tpl("member/mypage/orders");
    }

    /**
     * 마이 포인트
     * @param model
     * @return
     */
    @GetMapping("/point")
    public String point(Model model) {
        commonProcess("point", model);

        ListData<Point> point = pointInfoService.getList();

        model.addAttribute("totalPoint", pointInfoService.pointSum());
        model.addAttribute("items", point.getItems());
        model.addAttribute("pagination", point.getPagination());
        return utils.tpl("member/mypage/point");
    }

    /**
     * 최근 본 상품
     * @param model
     * @return
     */
    @GetMapping("/recentlyview")
    public String recentlyview(Model model) {
        commonProcess("recentlyview", model);
        return utils.tpl("member/mypage/recently_view");
    }



    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "myPage";
        String pageTitle = "마이페이지";

        List<String> addCommonScript = new ArrayList<>();    // 공통 자바스크립트
        List<String> addCommonCss = new ArrayList<>();    // 공통 자바스크립트
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if(mode.equals("orders") || mode.equals("myPage")){
            pageTitle = mode.equals("orders") ? "마이페이지" : "주문 내역";

            addCss.add("member/mypage/mypage");

            addCss.add("member/mypage/order");
        }else if(mode.equals("recentlyview")){
            addScript.add("member/mypage/recently");
            pageTitle = "최근 본 상품";
        }
        addCommonScript.add("tab");
        addCommonCss.add("tab");


        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addCommonCss", addCommonCss);
    }


}
