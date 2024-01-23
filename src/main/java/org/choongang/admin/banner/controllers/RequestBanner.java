package org.choongang.admin.banner.controllers;

import jakarta.persistence.Transient;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
public class RequestBanner {

    private String bannerWhere; // 배너 사용 위치

    private int horizontal; // 가로 크기

    private int vertical; // 세로 크기

    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @Transient
    private List<FileInfo> bannerImage; // 배너 이미지
}
