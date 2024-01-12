package org.choongang.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = @Index(name = "idx_pdt_opt_order", columnList = "listOrder DESC, createdAt ASC"))
public class ProductOption extends Base {

    @Id @GeneratedValue
    private Long seq;

    @Column(length = 80, nullable = false)
    private String name; // 옵션명

    private int addPrice; // 옵션 추가금액(-, +)

    private boolean userStock; // false : 무제한, true: 재고 0 -> 품절
    private int stock; // 옵션별 재고

    private int listOrder; // 진열 가중치, 번호가 클수록 앞에 진열

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productSeq")
    private Product product;
}
