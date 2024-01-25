package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;

@Entity
@Data
public class Point extends Base {

    @Id
    @GeneratedValue
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "memberSeq")
    private Member member;

    private int point;

}
