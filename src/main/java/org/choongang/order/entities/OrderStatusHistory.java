package org.choongang.order.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.order.constants.OrderStatus;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistory {

    @Id
    @GeneratedValue
    private Long seq;

    private Long orderSeq; // 주문번호

    @Enumerated(EnumType.STRING)
    @Column(length = 70, nullable = false)
    private OrderStatus prevStatus; // 이전단계

    @Enumerated(EnumType.STRING)
    @Column(length = 70, nullable = false)
    private OrderStatus status; // 현재 단계

    private boolean emailSent; // 메일 전송 여부


}
