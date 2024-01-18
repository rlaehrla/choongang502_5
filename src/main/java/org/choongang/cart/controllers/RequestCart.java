package org.choongang.cart.controllers;

import lombok.Data;

import java.util.List;

@Data
public class RequestCart {
    private String mode = "cart"; // cart : 장바구니 노출, direct : 바로구매

    private Long seq; // 상품번호

    private List<Integer> selectedNums; // 옵션 순번
}