package org.choongang.admin.banner.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.file.entities.FileInfo;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RequestBanner {

    private String mode = "add";

    private Long seq;

    @NotBlank
    private String groupCode; // 배너 그룹 코드

    @NotBlank
    private String bName;

    private String bLink; // 배너 링크
    private String target = "_self";

    private long listOrder;

    private boolean active;

    private MultipartFile[] files;

    private FileInfo bannerImage;
}