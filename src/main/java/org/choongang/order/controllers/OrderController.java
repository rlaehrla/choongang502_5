package org.choongang.order.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.controllers.PaymentConfig;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.cart.constants.CartType;
import org.choongang.cart.entities.CartInfo;
import org.choongang.cart.service.CartData;
import org.choongang.cart.service.CartDeleteService;
import org.choongang.cart.service.CartInfoService;
import org.choongang.cart.service.CartSaveService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.Point;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.member.repositories.PointRepository;
import org.choongang.member.service.PointInfoService;
import org.choongang.order.constants.OrderStatus;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.order.repositories.OrderItemRepository;
import org.choongang.order.service.OrderInfoService;
import org.choongang.order.service.OrderItemInfoService;
import org.choongang.order.service.OrderSaveService;
import org.choongang.order.service.OrderStatusService;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.eclipse.angus.mail.imap.protocol.MODSEQ;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements ExceptionProcessor {

    private final CartInfoService cartInfoService;
    private final MemberUtil memberUtil;
    private final AddressRepository addressRepository;
    private final Utils utils;
    private final OrderValidator validator;
    private final OrderSaveService orderSaveService;
    private final OrderInfoService orderInfoService;
    private final PointInfoService pointInfoService;
    private final ConfigInfoService configInfoService;
    private final PointRepository pointRepository;
    private final OrderStatusService orderStatusService;
    private final OrderItemRepository orderItemRepository;

    @ModelAttribute("paymentConfig")
    public PaymentConfig paymentConfig() {
        return configInfoService.get("paymentConfig", PaymentConfig.class).orElseGet(PaymentConfig::new);
    }

    /**
     * 주문서 작성
     *
     * @param seq : 장바구니 등록 번호
     *              장바구니 등록번호가 없으면 바로구매 : CartType.DIRECT
     *                  바로구매(DIRECT) : 상품 상세에서 바로 주문하는 경우
     *                  CART : 장바구니 -> 주문하기
     * @param model
     * @return
     * */


    @GetMapping
    public String order(@RequestParam(name="seq", required = false) List<Long> seq, @ModelAttribute RequestOrder form, Model model) {
        commonProcess("order", model);

        CartType mode = seq == null || seq.isEmpty() ? CartType.DIRECT : CartType.CART;
        model.addAttribute("cartType", mode);
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

        if(memberUtil.isLogin()){
            model.addAttribute("point", pointInfoService.pointSum());
        }

        return utils.tpl("order/order_form");
    }

    @PostMapping
    public String orderPs(@Valid RequestOrder form, Errors errors, Model model){
        commonProcess("order", model);
        validator.validate(form, errors);

        if(errors.hasErrors()){
            model.addAttribute("requestOrder", form);

            List<Long> seq = form.getCartSeq();
            CartType cartType = form.getCartType();
            CartData cartData = cartInfoService.getCartInfo(cartType, seq);
            model.addAttribute("cartType", cartType);
            model.addAttribute("mode", "order");
            model.addAttribute("cartData", cartData);
            return utils.tpl("order/order_form");
        }

        OrderInfo orderInfo = orderSaveService.save(form);
        Long seq = orderInfo.getSeq();
        List<OrderItem> orderItems = orderItemRepository.findByOrderInfoSeq(seq);
        if(orderInfo.getPayPrice() == 0){
            String script = "alert('" + Utils.getMessage("주문완료", "commons")+ "');"
                    + "location.replace('/order/detail/"+ seq+"');";
            orderStatusService.change(seq, OrderStatus.IN_CASH);

            model.addAttribute("script", script);

            return "common/_execute_script";
        }else{

            String productNm = orderItems.get(0).getProductName();

            if(orderItems.size() > 1){
                productNm += "외 " + (orderItems.size() - 1) + "건";
            }
            int payPrice = orderInfo.getPayPrice();

            String script = "const prName='" + productNm + "'; const payPrice=" + payPrice + "; const seq = " + seq + ";";

            model.addAttribute("script", script);

            return utils.tpl("order/payment");
        }

    }

    @GetMapping("/paySuccess/{seq}")
    public String paySuccess(@PathVariable("seq") Long seq){

        orderStatusService.change(seq, OrderStatus.IN_CASH);
        OrderInfo orderInfo = orderInfoService.get(seq);

        /* 포인트 적립 S */
        int pt = (int)Math.round(0.05 * orderInfo.getPayPrice());

        Point point = Point.builder()
                .member((Member)memberUtil.getMember())
                .point(pt)
                .orderNo(orderInfo.getOrderNo())
                .build();
        pointRepository.saveAndFlush(point);
        /* 포인트 적립 E */

        return "redirect:/order/detail/" + seq;
    }

    @GetMapping("/payFail")
    public String payFail(Model model){

        String script="alert('결제를 취소하였습니다.');"
                +"location.replace('/cart');";

        model.addAttribute("script", script);
        return "common/_execute_script";
    }

    @GetMapping("/detail/{seq}")
    public String detail(@PathVariable("seq") Long seq, Model model){
         commonProcess("detail", model);

         OrderInfo orderInfo = orderInfoService.get(seq);


         int getPoint = (int) Math.round(orderInfo.getPayPrice()* 0.05);
         model.addAttribute("getPoint", getPoint);
         model.addAttribute("orderInfo", orderInfo);
         return utils.tpl("order/order_detail");
    }


    /**
     * 주문 공통 처리
     *
     * @param mode
     * @param model
     */

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "order";
        String pageTitle = "주문하기";

        List<String> addCommonScript = new ArrayList<>();    // 공통 자바스크립트
        List<String> addScript = new ArrayList<>();    // 프론트 자바스크립트
        List<String> addCss = new ArrayList<>();
        if(mode.equals("order")){
            pageTitle = "주문하기";
            addCommonScript.add("address");
            addScript.add("order/order");
            addCss.add("order/form");
            addCss.add("cart/cart");
        }else if(mode.equals("detail")){
            addCss.add("order/detail");
            pageTitle = "주문상세";
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("mode", mode);
        model.addAttribute("addCommonScript", addCommonScript) ;
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }
}
