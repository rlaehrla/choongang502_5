package org.choongang.order.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Farmer;
import org.choongang.order.constants.OrderStatus;
import org.choongang.product.entities.Product;
import org.choongang.product.entities.ProductOption;

/**
 * 주문 상품
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends Base {

    @Id
    @GeneratedValue
    private Long seq; // 상품당 주문 번호

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private OrderStatus status = OrderStatus.READY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderInfoSeq")
    private OrderInfo orderInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productSeq")
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productOptionSeq")
    private ProductOption option;

    private int ea = 1; // 주문 수량

    @Column(length = 150, nullable = false)
    private String productName;
    private String optionName;
    private String optionValue;

    private int salePrice;

    private int totalPrice;
    private int totalDiscount;

    @Column(length = 60)
    private String deliveryCompany; // 배송업체
    @Column(length = 60)
    private String deliveryInvoice; // 운송장번호

}
