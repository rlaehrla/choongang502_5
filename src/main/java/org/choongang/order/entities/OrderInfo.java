package org.choongang.order.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.cart.service.CartData;
import org.choongang.commons.AddressAssist;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.Point;
import org.choongang.order.constants.OrderStatus;
import org.choongang.order.constants.PayType;

import java.util.ArrayList;
import java.util.List;

/**
 * 주문 정보
 */
@Entity
@Data
public class OrderInfo extends Base {

    @Id @GeneratedValue
    private Long seq;

    @Column(unique = true)
    private Long orderNo = System.currentTimeMillis();

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private OrderStatus status = OrderStatus.READY;

    // 로그인한 멤버의 경우 멤버 저장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberSeq")
    private AbstractMember member;

    @Column(length = 40, nullable = false)
    private String orderName; // 주문자 이름

    @Column(length = 15, nullable = false)
    private String orderCellphone; // 주문자 번호

    @Column(length = 90, nullable = false)
    private String orderEmail; // 주문자 이메일

    @Column(length = 40, nullable = false)
    private String receiverName; // 받는분 이름

    @Column(length = 15, nullable = false)
    private String receiverCellphone; // 받는분 번호


    @Column(length=10, nullable = false)
    private String zoneCode;

    @Column(length=100, nullable = false)
    private String address;

    @Column(length=100)
    private String addressSub;

    @Column(length = 150)
    private String deliveryMemo; // 배송 메모

    private int totalPrice; // 주문시점 상품 합계
    private int totalDeliveryPrice; // 주문 시점 배송비
    private int totalDiscount; // 주문 시점 총 할인금액
    private int payPrice; // 주문 시점 결제 금액

    private int usePoint; // 포인트 사용 금액

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PayType payType;

    private String depositor; // 무통장 입금 입금자명


    @ToString.Exclude
    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();


}
