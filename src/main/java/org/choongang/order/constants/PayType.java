package org.choongang.order.constants;

import lombok.Getter;

public enum PayType {
    LBT("무통장 입금"),    // 무통장 입금
    VACCOUNT("가상계좌"),  // 가상계좌
    CARD("신용카드"),      // 신용카드
    DIRECT("계좌이체") ;   // 계좌이체

    @Getter
    private final String title;

    PayType(String title) { this.title = title ;}
}
