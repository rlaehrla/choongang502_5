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

}
