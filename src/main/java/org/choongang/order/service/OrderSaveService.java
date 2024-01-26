package org.choongang.order.service;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.cart.entities.CartInfo;
import org.choongang.cart.repositories.CartInfoRepository;
import org.choongang.cart.service.CartData;
import org.choongang.cart.service.CartDeleteService;
import org.choongang.cart.service.CartInfoService;
import org.choongang.commons.AddressAssist;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.Point;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.member.repositories.PointRepository;
import org.choongang.member.service.AddressSaveService;
import org.choongang.member.service.PointInfoService;
import org.choongang.member.service.PointSaveService;
import org.choongang.order.constants.OrderStatus;
import org.choongang.order.constants.PayType;
import org.choongang.order.controllers.RequestOrder;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.order.repositories.OrderItemRepository;
import org.choongang.product.entities.Product;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderSaveService {
    private final OrderItemRepository orderItemRepository;
    private final AddressSaveService addressSaveService;
    private final OrderInfoRepository orderInfoRepository;
    private final CartInfoService cartInfoService;
    private final OrderInfoService orderInfoService ;
    private final MemberUtil memberUtil;
    private final AddressRepository addressRepository;
    private final HttpServletRequest request ;
    private final PointInfoService pointInfoService;
    private final PointSaveService pointSaveService;
    private final PointRepository pointRepository;
    private final CartDeleteService cartDeleteService;

    public OrderInfo save(RequestOrder form){
        List<Long> cartSeqs = form.getCartSeq();
        CartData cartData = cartInfoService.getCartInfo(cartSeqs);

        List<CartInfo> cartItems = cartData.getItems();
        int totalPrice = cartData.getTotalPrice();
        int totalDiscount = cartData.getTotalDiscount();
        int totalDeliveryPrice = cartData.getTotalDeliveryPrice();
        int payPrice = cartData.getPayPrice() - form.getUsePoint();

        /* 장바구니에서 주문 상품 삭제 S */
        cartDeleteService.deleteCart(cartData);
        /* 장바구니에서 주문 상품 삭제 E */


        /* 주소 저장 S */
        AddressAssist address = form.getAddr();

        if(memberUtil.isLogin()){
            Address addr = addressRepository.findExists(memberUtil.getMember().getSeq(), address).orElse(null);
            if(addr != null) {
                addressSaveService.edit(addr.getSeq(), address);
            }else{
                addressSaveService.save(memberUtil.getMember().getSeq(), address);
            }
        }

        /* 주소 저장 E */



        /* 주문 정보 저장 S */
        OrderInfo orderInfo = new OrderInfo();

        orderInfo.setOrderName(form.getOrderName());
        orderInfo.setOrderCellphone(form.getOrderCellPhone());
        orderInfo.setOrderEmail(form.getOrderEmail());
        orderInfo.setReceiverName(form.getReceiverName());
        orderInfo.setReceiverCellphone(form.getReceiverCellPhone());
        orderInfo.setDeliveryMemo(form.getDeliveryMemo());
        orderInfo.setStatus(OrderStatus.READY);
        orderInfo.setPayType(PayType.valueOf(form.getPayType()));
        orderInfo.setTotalPrice(totalPrice);
        orderInfo.setTotalDiscount(totalDiscount);
        orderInfo.setTotalDeliveryPrice(totalDeliveryPrice);
        orderInfo.setPayPrice(payPrice);
        orderInfo.setZoneCode(form.getAddr().getZoneCode());
        orderInfo.setAddress(form.getAddr().getAddress());
        orderInfo.setAddressSub(form.getAddr().getAddressSub());
        orderInfo.setUsePoint(form.getUsePoint());

        if(memberUtil.isLogin()){
            orderInfo.setMember(memberUtil.getMember());
        }


        orderInfoRepository.saveAndFlush(orderInfo);
        /* 주문 정보 저장 E */

        /* 포인트 사용 저장 S */
        if(form.getUsePoint() != 0){
            Point usePoint = Point.builder()
                    .point(form.getUsePoint() * -1)
                    .member((Member)memberUtil.getMember())
                    .orderNo(orderInfo.getOrderNo())
                    .build();
            System.out.println("사용포인트 : " + usePoint);
            pointRepository.saveAndFlush(usePoint);

        }
        /* 포인트 사용 저장 E */
        /* 포인트 적립 S */

        int pt = (int)Math.round(0.05 * (totalPrice - form.getUsePoint()));

        Point point = Point.builder()
                .member((Member)memberUtil.getMember())
                .point(pt)
                .orderNo(orderInfo.getOrderNo())
                .build();
        System.out.println("적립포인트 : " + point);
        pointRepository.saveAndFlush(point);
        /* 포인트 적립 E */

        /* 주문 상품 정보 저장 S */
        List<OrderItem> items = new ArrayList<>();

        for(CartInfo cartItem : cartItems){
            Product product = cartItem.getProduct();
            OrderItem item = OrderItem.builder()
                    .product(product)
                    .optionName(product.getOptionName())
                    .status(orderInfo.getStatus())
                    .productName(product.getName())
                    .ea(cartItem.getEa())
                    .salePrice(product.getSalePrice())
                    .totalDiscount(cartItem.getTotalDiscount())
                    .totalPrice(cartItem.getTotalPrice())
                    .orderInfo(orderInfo)
                    .build();
            items.add(item);
        }

        items.sort(Comparator.comparingLong(o -> o.getProduct().getFarmer().getSeq()));

        orderItemRepository.saveAllAndFlush(items);
        /* 주문 상품 정보 저장 E */



        return orderInfo;

    }


    /**
     * 주문 상태 변경 저장
     */
    public void statusSave(Long orderSeq, String status) {
        status = request.getParameter("orderStatus") ;

        OrderInfo info = orderInfoService.get(orderSeq) ;
        info.setStatus(OrderStatus.valueOf(status));

        orderInfoRepository.saveAndFlush(info) ;

        OrderItem item = orderItemRepository.findById(orderSeq).orElseGet(null) ;
        item.setStatus(OrderStatus.valueOf(status));

        orderItemRepository.saveAndFlush(item) ;
    }
}
