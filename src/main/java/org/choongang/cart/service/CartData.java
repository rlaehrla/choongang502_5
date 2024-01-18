package org.choongang.cart.service;

import lombok.Builder;
import lombok.Data;
import org.choongang.cart.entities.CartInfo;

import java.util.List;

@Data
@Builder
public class CartData {
    private List<CartInfo> items; // 장바구니 상품 목록

    private int totalPrice; // 상품가 합계
    private int totalDiscount; // 할인금액 합계
    private int totalDeliveryPrice; // 배송비 합계
    private int payPrice; // 결제금액 합계 - 상품가 - 할인금액 + 배송비
}