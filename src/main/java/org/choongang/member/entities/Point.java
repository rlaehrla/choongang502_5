package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.order.entities.OrderInfo;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point extends Base {

    @Id
    @GeneratedValue
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "memberSeq")
    private Member member;

    private int point;

    private Long orderNo;

    @Column(length=60)
    private String message;


}
