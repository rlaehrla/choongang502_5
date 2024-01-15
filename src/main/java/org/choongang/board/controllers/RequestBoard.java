package org.choongang.board.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

// 작성에 필요한 부분들
@Data
public class RequestBoard {
    private String mode = "write";
    private Long seq; // 게시글 번호
    private String bid; // 게시판 ID
    private String gid = UUID.randomUUID().toString();

    @NotBlank
    private String poster; // 글 작성자
    private String questPw; // 비회원 비밀번호

    private Boolean notice; // 공지사항 여부

    @NotBlank
    private String subject; // 글 제목
    @NotBlank
    private String content; // 글 내용

}