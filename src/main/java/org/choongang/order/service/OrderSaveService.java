package org.choongang.order.service;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.cart.entities.CartInfo;
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
import org.choongang.order.constants.OrderStatus;
import org.choongang.order.controllers.RequestOrder;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.order.repositories.OrderItemRepository;
import org.choongang.product.entities.Product;
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
    private final PointRepository pointRepository;
    private final CartDeleteService cartDeleteService;
    private final OrderStatusService orderStatusService;

    public OrderInfo save(RequestOrder form){
        List<Long> cartSeqs = form.getCartSeq();
        CartData cartData = cartInfoService.getCartInfo(cartSeqs);

        List<CartInfo> cartItems = cartData.getItems();
        int totalPrice = cartData.getTotalPrice();
        int totalDiscount = cartData.getTotalDiscount();
        int totalDeliveryPrice = cartData.getTotalDeliveryPrice();
        int payPrice = cartData.getPayPrice() - form.getUsePoint();


        /* 주소 저장 S */
        AddressAssist address = AddressAssist.builder()
                .zoneCode(form.getZoneCode())
                .address(form.getAddress())
                .addressSub(form.getAddressSub())
                .build();

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
        orderInfo.setTotalPrice(totalPrice);
        orderInfo.setTotalDiscount(totalDiscount);
        orderInfo.setTotalDeliveryPrice(totalDeliveryPrice);
        orderInfo.setPayPrice(payPrice);
        orderInfo.setZoneCode(form.getZoneCode());
        orderInfo.setAddress(form.getAddress());
        orderInfo.setAddressSub(form.getAddressSub());
        orderInfo.setUsePoint(form.getUsePoint());

        if(memberUtil.isLogin()){
            orderInfo.setMember(memberUtil.getMember());
        }


        orderInfoRepository.saveAndFlush(orderInfo);
        /* 주문 정보 저장 E */

        if(memberUtil.isLogin()){
            /* 포인트 사용 저장 S */
            if(form.getUsePoint() != 0){
                Point usePoint = Point.builder()
                        .point(form.getUsePoint() * -1)
                        .member((Member)memberUtil.getMember())
                        .orderNo(orderInfo.getOrderNo())
                        .build();
                pointRepository.saveAndFlush(usePoint);

            }
            /* 포인트 사용 저장 E */


        }

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

        /* 장바구니에서 주문 상품 삭제 */
        cartDeleteService.deleteCart(cartData);

        orderStatusService.change(orderInfo.getSeq(), OrderStatus.ORDER);

        return orderInfo;

    }


    /**
     * 배송 정보 변경 저장
     */
    public void updateDelivery(Long orderSeq) {
        String deliveryCompany = request.getParameter("deliveryCompany") ;
        String deliveryInvoice = request.getParameter("deliveryInvoice") ;

        if (deliveryCompany == null || deliveryInvoice == null) { return; }

        OrderItem item = orderItemRepository.findById(orderSeq).orElse(null) ;
        item.setDeliveryCompany(deliveryCompany);
        item.setDeliveryInvoice(deliveryInvoice);

        orderItemRepository.saveAndFlush(item) ;
    }
}
