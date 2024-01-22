package org.choongang.order.constants;

public enum OrderStatus {
    READY, // 주문 접수 전
    ORDER, // 주문 접수
    IN_CASH, // 입금 확인
    PREPARE, // 상품 준비중
    DELIVERY, // 배송중
    ARRIVAL, // 배송완료
    DONE, // 주문완료
    CANCEL, // 입금확인 전 취소
    REFUND, // 입금 후 취소
    EXCHANGE // 교환

}
