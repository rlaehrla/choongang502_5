package org.choongang.order.controllers;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderSearch {

    private int page = 1;
    private int limit = 20;

    private String productNm ;    // 상품명
    private List<String> orderStatus;    // 주문 상태

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드

    private LocalDate sdate; // 등록일 기준 날짜 검색
    private LocalDate edate;
}
