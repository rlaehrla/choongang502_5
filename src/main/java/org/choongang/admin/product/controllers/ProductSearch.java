package org.choongang.admin.product.controllers;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductSearch {
    private int page = 1;
    private int limit = 20;

    private List<String> cateCd;
    private List<Long> seq;
    private String name;

    private List<String> statuses;

    private LocalDate sdate;
    private LocalDate edate;
}
