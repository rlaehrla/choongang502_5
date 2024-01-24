package org.choongang.admin.banner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Banner extends Base {

    @Id @GeneratedValue
    private Long seq; // 배너 번호

    private String bannerWhere; // 배너 사용 위치

    @Column
    private String horizontal; // 가로 크기

    @Column
    private String vertical; // 세로 크기

    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @Transient
    private List<FileInfo> bannerImage; // 배너 이미지

}
