package org.choongang.member.controllers;

import lombok.Data;

@Data
public class MemberSearch {
    private int page = 1;
    private int limit = 20;
    private String userName; // 회원명검색
    private String userId; // 아이디 검색
    private String email; // 이메일 검색
    private String nickName; // 닉네임 검색
    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드
}
