package org.choongang.recipe.controllers;

import lombok.Data;

@Data
public class RecipeDataSearch {
    private int page = 1; // 기본값 1page
    private int limit; // 0 : 설정에 있는 1페이지 게시글 갯수, 1이상 : 지정한 갯수

    /**
     * rcpName : 요리이름
     * requiredIng  : 재료
     * rcpName_ing: 요리 + 재료 (OR)
     *
     * hashtag : 태그
     *
     * poster : 닉네임 + 아이디 + 이름 (OR)
     * ALL : 다 포함
     *
     * 요리분류 + 요리 종류 -> 필터로?
     */
    private String rcpName;
    private String requiredIng;

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드




}
