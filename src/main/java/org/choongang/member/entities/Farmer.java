package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 판매회원 = 농부
 */
@Data
@Entity
@DiscriminatorValue("F")
public class Farmer extends AbstractMember {

    @Column(length = 90)
    private String farmTitle ;    // 농장 이름

    @Column(length = 15)
    private String businessPermitNum ;    // 사업자등록 번호

    @Transient    // 가공될 데이터
    private FileInfo businessPermit ;    // 사업자등록증 파일
}
