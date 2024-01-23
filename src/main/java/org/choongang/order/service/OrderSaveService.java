package org.choongang.order.service;


import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.choongang.cart.entities.CartInfo;
import org.choongang.cart.service.CartData;
import org.choongang.cart.service.CartInfoService;
import org.choongang.commons.AddressAssist;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Address;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.member.service.AddressSaveService;
import org.choongang.order.constants.OrderStatus;
import org.choongang.order.constants.PayType;
import org.choongang.order.controllers.RequestOrder;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.order.repositories.OrderItemRepository;
import org.choongang.product.entities.Product;
import org.modelmapper.ModelMapper;
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
    private final MemberUtil memberUtil;
    private final AddressRepository addressRepository;

    public OrderInfo save(RequestOrder form){
        List<Long> cartSeqs = form.getCartSeq();
        CartData cartData = cartInfoService.getCartInfo(cartSeqs);

        List<CartInfo> cartItems = cartData.getItems();
        int totalPrice = cartData.getTotalPrice();
        int totalDiscount = cartData.getTotalDiscount();
        int totalDeliveryPrice = cartData.getTotalDeliveryPrice();
        int payPrice = cartData.getPayPrice();


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
        orderInfo.setSeq(5L);

        if(memberUtil.isLogin()){
            orderInfo.setMember(memberUtil.getMember());
        }


        orderInfoRepository.saveAndFlush(orderInfo);
        /* 주문 정보 저장 E */

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
}
