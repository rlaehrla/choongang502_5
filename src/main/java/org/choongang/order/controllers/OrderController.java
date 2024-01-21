package org.choongang.order.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.cart.constants.CartType;
import org.choongang.cart.service.CartData;
import org.choongang.cart.service.CartInfoService;
import org.choongang.commons.AddressAssist;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.order.service.OrderBadException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements ExceptionProcessor {

    private final CartInfoService cartInfoService;
    private final MemberUtil memberUtil;
    private final AddressRepository addressRepository;
    private final Utils utils;
    private final OrderValidator orderValidator;

    */
/**
     * 주문서 작성
     *
     * @param seq : 장바구니 등록 번호
     *              장바구니 등록번호가 없으면 바로구매 : CartType.DIRECT
     *                  바로구매(DIRECT) : 상품 상세에서 바로 주문하는 경우
     *                  CART : 장바구니 -> 주문하기
     * @param model
     * @return
     *//*

    @GetMapping
    public String order(@RequestParam(name="seq", required = false) List<Long> seq,  Model model) {
        commonProcess("order", model);

        CartType mode = seq == null || seq.isEmpty() ? CartType.DIRECT : CartType.CART;
        CartData data = cartInfoService.getCartInfo(mode, seq);
        AbstractMember member = memberUtil.getMember();

        if(member != null){
            Long userSeq = member.getSeq();
            Address defaultAddr = addressRepository.findDefaultAddress(userSeq).orElse(null);

            if(defaultAddr != null){
                model.addAttribute("addr", defaultAddr);
            }
        }
        model.addAttribute("cartData", data);

        return utils.tpl("order/order_form");
    }

    @PostMapping
    public String orderPs(RequestOrder form, Errors errors, Model model){

        orderValidator.validate(form, errors);

        if(errors.hasErrors()){
            throw new OrderBadException();
        }



        return "";
    }


    */
/**
     * 주문 공통 처리
     *
     * @param mode
     * @param model
     *//*

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "order";
        String pageTitle = "주문하기";

        List<String> addCommonScript = new ArrayList<>();    // 공통 자바스크립트
        List<String> addScript = new ArrayList<>();    // 프론트 자바스크립트

        if(mode.equals("order")){
            pageTitle = "주문하기";
            addCommonScript.add("address");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("mode", mode);
        model.addAttribute("addCommonScript", addCommonScript) ;
    }
}*/
