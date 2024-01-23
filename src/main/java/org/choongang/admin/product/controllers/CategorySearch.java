package org.choongang.admin.product.controllers;

import lombok.Data;

import java.time.Month;
import java.util.List;

@Data
public class CategorySearch {
    private String mainCategory;
    private List<String> cateCd;
    private List<String> months;

    private String sopt;
    private String skey;
}

