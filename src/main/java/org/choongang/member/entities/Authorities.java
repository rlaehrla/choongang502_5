package org.choongang.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.choongang.member.constants.Authority;

@Data
@Entity
@Table(indexes=@Index(name="uq_member_authority", columnList = "member_seq, authority", unique = true))
public class Authorities {
    @Id
    @GeneratedValue
    private Long seq;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_seq")
    private AbstractMember member;

    @Enumerated(EnumType.STRING)
    @Column(length=15, nullable = false)
    private Authority authority;
}
