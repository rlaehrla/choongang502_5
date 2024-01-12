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
public class Product extends Base {

    @Id @GeneratedValue
    private Long seq; // 상품 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cateCd")
    private Category category; // 상품 분류

    private String name; // 상품명

    private int consumerPrice; // 소비자가(보이는 금액)
    private int salePrice; // 판매가(결제 기준 금액)

    private int stock; // 옵션을 사용하지 않는 경우 단일 상품 재고, 0 - 무제한

}
