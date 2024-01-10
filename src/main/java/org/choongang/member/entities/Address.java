package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends Base {

    // 한 회원이 주소 여러개 저장할 수 있게 저장
    @ManyToOne
    @JoinColumn(name="member_seq")
    private AbstractMember member;

    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 60)
    private String title; // 주소 명칭 ex - 우리집


    private boolean defaultAddress; // 기본배송지인지 확인

    @Column(length = 10, nullable = false)
    private String zoneCode;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 100)
    private String addressSub;
}
