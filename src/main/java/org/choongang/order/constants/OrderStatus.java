package org.choongang.order.constants;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    READY("주문 접수 전"), // 주문 접수 전
    ORDER("주문 접수"), // 주문 접수
    IN_CASH("입금 확인"), // 입금 확인
    PREPARE("상품 준비중"), // 상품 준비중
    DELIVERY("배송중"), // 배송중
    ARRIVAL("배송완료"), // 배송완료
    DONE("주문완료"), // 주문완료
    CANCEL("입금확인 전 취소"), // 입금확인 전 취소
    REFUND("입금 후 취소"), // 입금 후 취소
    EXCHANGE("교환"); // 교환

    private final String title;

    OrderStatus(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public List<String[]> getTitles() {
        return Arrays.asList(
                new String[]{READY.name(), READY.title},
                new String[]{ORDER.name(), ORDER.title},
                new String[]{IN_CASH.name(), IN_CASH.title},
                new String[]{PREPARE.name(), PREPARE.title},
                new String[]{DELIVERY.name(), DELIVERY.title},
                new String[]{ARRIVAL.name(), ARRIVAL.title},
                new String[]{DONE.name(), DONE.title},
                new String[]{CANCEL.name(), CANCEL.title},
                new String[]{REFUND.name(), REFUND.title},
                new String[]{EXCHANGE.name(), EXCHANGE.title}
        );
    }


}
