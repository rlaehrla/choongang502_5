package org.choongang.board.controllers;

import lombok.Data;

import java.util.List;

@Data
public class BoardDataSearch {
    private int page = 1; // 기본값 1page
    private int limit; // 0 : 설정에 있는 1페이지 게시글 갯수, 1이상 : 지정한 갯수

    /**
     * 검색 옵션
     *
     * subject : 제목
     * content : 내용
     * subject_content : 제목 + 내용 (OR)
     * poster : 작성자명 + 아이디 + 회원 이름 (OR)
     * ALL : 다 포함해서
     */
    private String spot; // 검색 옵션
    private String skey; // 검색 키워드

    private List<String> bid; // 게시판 ID

    private String userId;
}
