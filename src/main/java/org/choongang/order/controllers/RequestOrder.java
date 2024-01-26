package org.choongang.order.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.cart.service.CartData;
import org.choongang.commons.AddressAssist;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.order.constants.PayType;

import java.util.List;

@Data
public class RequestOrder {

    private List<Long> cartSeq; // 장바구니 등록 번호

    @NotBlank
    private String orderName; // 주문자 이름

    @NotBlank
    private String orderCellPhone; // 주문자 번호

    @NotBlank
    @Email
    private String orderEmail; // 주문자 이메일

    @NotBlank
    private String receiverName; // 받는분 이름

    @NotBlank
    private String receiverCellPhone; // 받는분 번호

    @NotBlank
    private AddressAssist addr; // 받는사람 주소

    private String deliveryMemo; // 배송 메모

    private int usePoint = 0;

    @NotBlank
    private String payType = PayType.LBT.name(); // 결제 수단

    private String depositor; // 무통장 입금일 경우 입금자 명

    @AssertTrue
    private boolean agree; // 결제약관 동의 여부

}
