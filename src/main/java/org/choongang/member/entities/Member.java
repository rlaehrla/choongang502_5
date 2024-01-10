package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.member.constants.Gender;

import java.time.LocalDate;

/**
 * 일반회원
 */
@Data
@Entity
@DiscriminatorValue("M")
public class Member extends AbstractMember {

    @Column(length = 10)    // 성별은 필수 항목 X
    @Enumerated(EnumType.STRING)
    private Gender gender ;    // 성별

    private LocalDate birthdate ;
}
