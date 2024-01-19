package org.choongang.board.controllers.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestComment {
    // 나중에 수정용
    private String mode = "add";

    private Long seq; // 댓글 등록 번호

    private Long boardDataSeq; // 게시글 번호

    @NotBlank
    private String commenter; // 작성자

    private String guestPw; // 비회원 비밀번호

    @NotBlank
    private String content; // 댓글 내용


}
