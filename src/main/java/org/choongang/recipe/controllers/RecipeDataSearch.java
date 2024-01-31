package org.choongang.recipe.controllers;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RecipeDataSearch {
    private int page = 1; // 기본값 1page
    private int limit = 10; // 0 : 설정에 있는 1페이지 게시글 갯수, 1이상 : 지정한 갯수


    /**
     * rcpName : 요리이름
     *
     * requiredIng  : 재료
     *
     * poster : 닉네임 + 아이디 (OR)
     *
     * ALL : 다 포함
     *
     * 요리 분류 + 요리 종류 -> 미정
     */
    private String rcpName;
    private String requiredIng;

    private String nickName;

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드

    private String cateCd;

    // 관리자 페이지
    private List<Long> seq;
    private LocalDate sdate; // 등록일 기준 날짜 검색
    private LocalDate edate;




}
