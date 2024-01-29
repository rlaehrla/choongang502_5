package org.choongang.admin.banner.controllers;

import lombok.Data;

@Data
public class BannerGroupSearch {
    private int page = 1;
    private int limit = 20;

    private String sopt;
    private String skey;
}