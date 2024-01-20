package org.choongang.order.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.choongang.cart.service.CartData;
import org.choongang.commons.AddressAssist;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;

@Entity
@Data

public class Order {

    @Id
    @GeneratedValue
    private Long seq;

    // 장바구니 내역
    private CartData cartData;

    // 로그인한 멤버의 경우 멤버 저장
    private AbstractMember member;

    // 비회원 -> 비밀번호로 주문내역 접근 가능하도록
    private final String password;

    private String orderName; // 주문자 이름
    private String orderCellphone; // 주문자 번호
    private String orderEmail; // 주문자 이메일

    private String receiverName; // 받는분 이름
    private String receiverCellphone; // 받는분 번호

    private Address addr; // 받는사람 주소

    // 결제 정보
    // enum클래스 사용해야될거같음

    private boolean agree; // 결제약관 동의 여부


}
