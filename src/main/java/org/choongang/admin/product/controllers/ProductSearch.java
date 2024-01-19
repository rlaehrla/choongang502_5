package org.choongang.admin.product.controllers;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductSearch {
    private int page = 1;
    private int limit = 20;

    private List<String> cateCd;  // 분류 코드
    private List<Long> seq;
    private String name;

    private List<String> statuses;

    private LocalDate sdate; // 등록일 기준 날짜 검색
    private LocalDate edate;
}
