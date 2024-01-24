package org.choongang.admin.banner.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
public class RequestBanner {

    private String mode = "add";

    private Long seq; // 배너 번호

    @NotBlank
    private String bannerWhere; // 배너 사용 위치

    @NotBlank
    @Size(max = 1000, message = "가로 크기는 최대 1000까지 입력 가능합니다.")
    private String horizontal; // 가로 크기

    @NotBlank
    @Size(max = 500, message = "세로 크기는 최대 500까지 입력 가능합니다.")
    private String vertical; // 세로 크기

    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    private List<FileInfo> bannerImage; // 배너 이미지
}
