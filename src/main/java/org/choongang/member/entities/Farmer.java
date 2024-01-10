package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Farmer extends Base {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=65, nullable = false)
    private String gid;

    @Column(length=80, nullable = false, unique = true)
    private String email;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long tel ;

    @ToString.Exclude
    @OneToMany(mappedBy = "farmer", fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();

    @Transient
    private FileInfo profileImage;

    @Column(length = 40, nullable = false)
    private String farmTitle ;    // 농장 이름

    @Column(length = 80, nullable = false)
    private String farmAddress ;    // 농장 주소

    @Column(length = 10, nullable = false)
    private String certificateNo ;    // 사업자등록증 번호

    @Transient
    private FileInfo certificateFile ;    // 사업자등록증 파일
}
