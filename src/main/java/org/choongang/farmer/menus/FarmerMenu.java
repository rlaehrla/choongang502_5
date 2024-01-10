package org.choongang.farmer.menus;

import org.choongang.commons.MenuDetail;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FarmerMenu {
    private final static Map<String, List<MenuDetail>> farmerMenus;

    static {
        farmerMenus = new HashMap<>();
        farmerMenus.put("blog", Arrays.asList(
           new MenuDetail("intro", "소개설정", "/farm/manage/blog"),
           new MenuDetail("sales", "판매글설정", "/farm/manage/blog/sales"),
           new MenuDetail("review", "후기관리", "/farm/manage/blog/review"),
           new MenuDetail("sns", "소식설정", "/farm/manage/blog/sns")
        ));
        farmerMenus.put("order", Arrays.asList(
                new MenuDetail("order", "주문설정", "")
        ));
        farmerMenus.put("sales", Arrays.asList(
                new MenuDetail("status", "매출현황", ""),
                new MenuDetail("product", "상품관리", "")
        ));
    }

    public static List<MenuDetail> getMenus(String code){
        return farmerMenus.get(code);
    }
}
