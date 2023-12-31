package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.member.Authority;

@Data
@Entity
@Table(indexes=@Index(name="uq_member_authority", columnList = "member_seq, authority", unique = true))
public class Authorities {
    @Id
    @GeneratedValue
    private Long seq;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_seq")
    private Member member;    // 일반회원

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="farmer_seq")
    private Farmer farmer;    // 농장주(판매자)

    @Enumerated(EnumType.STRING)
    @Column(length=15, nullable = false)
    private Authority authority;
}
