package org.choongang.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;
import org.choongang.product.entities.Product;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWish extends Base {

    @Id
    @GeneratedValue
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "memberSeq")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productSeq")
    private Product product;

}
