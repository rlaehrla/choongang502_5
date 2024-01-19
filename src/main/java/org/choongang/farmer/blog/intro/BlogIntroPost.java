package org.choongang.farmer.blog.intro;

import jakarta.persistence.Transient;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
public class BlogIntroPost {

    private String introContent ;    // 블로그 소개글 본문 내용

    private String gid = UUID.randomUUID().toString() ;

    @Transient
    private List<FileInfo> introImages;    // 소개글 본문에 들어가는 이미지 파일
}
