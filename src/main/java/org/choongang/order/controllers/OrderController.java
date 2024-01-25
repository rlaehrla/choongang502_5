package org.choongang.order.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.controllers.PaymentConfig;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.cart.constants.CartType;
import org.choongang.cart.service.CartData;
import org.choongang.cart.service.CartDeleteService;
import org.choongang.cart.service.CartInfoService;
import org.choongang.cart.service.CartSaveService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.member.service.PointInfoService;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.service.OrderInfoService;
import org.choongang.order.service.OrderNotFoundException;
import org.choongang.order.service.OrderSaveService;
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
    private final CartDeleteService cartDeleteService;
    private final ProductInfoService productInfoService;
    private final CartSaveService cartSaveService;
    private final PointInfoService pointInfoService;
    private final ConfigInfoService configInfoService;

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
    public String order(@RequestParam(name="seq", required = false) List<Long> seq, Model model) {
        commonProcess("order", model);

        // 결제약관 불러오기
        PaymentConfig paymentConfig = configInfoService.get("paymentConfig", PaymentConfig.class).orElseGet(PaymentConfig::new);
        String personalInfoEntrust = paymentConfig.getPersonalInfoEntrust();

        System.out.println(personalInfoEntrust);

        model.addAttribute("personalInfoEntrust", personalInfoEntrust);

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

        if(memberUtil.isLogin()){
            model.addAttribute("point", pointInfoService.pointSum());
        }

        return utils.tpl("order/order_form");
    }

    @PostMapping
    public String orderPs(RequestOrder form, Errors errors, Model model){

        validator.validate(form, errors);

        if(errors.hasErrors()){
            return utils.tpl("/");
        }

        OrderInfo orderInfo = orderSaveService.save(form);

        String script = "alert('" + Utils.getMessage("주문완료", "commons")+ "');"
                + "location.replace('/order/detail/"+ orderInfo.getSeq()+"');";


        model.addAttribute("script", script);


        return "common/_execute_script";
    }

    @GetMapping("/detail/{seq}")
    public String detail(@PathVariable("seq") Long seq, Model model){
         commonProcess("detail", model);

         OrderInfo orderInfo = orderInfoService.get(seq);

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
