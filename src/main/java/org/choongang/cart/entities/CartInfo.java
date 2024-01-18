package org.choongang.cart.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.cart.constants.CartType;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;
import org.choongang.product.entities.Product;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
public class CartInfo extends Base {
    @Id @GeneratedValue
    private Long seq;

    @Enumerated(EnumType.STRING)
    @Column(name="_mode", length=10, nullable = false)
    private CartType mode = CartType.CART;

    @Column(name="_uid", nullable = false) // 비회원을 구분하기 위한 UID
    private int uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productSeq")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberSeq")
    private Member member;

    private int ea = 1; // 주문수량
}