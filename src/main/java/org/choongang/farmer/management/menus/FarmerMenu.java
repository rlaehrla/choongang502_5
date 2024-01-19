package org.choongang.farmer.management.menus;

import org.choongang.commons.MenuDetail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FarmerMenu {
    private final static Map<String, List<MenuDetail>> farmerMenus;

    static {
        farmerMenus = new HashMap<>();
        farmerMenus.put("blog", Arrays.asList(
           new MenuDetail("intro", "소개글관리", "/farmer/myFarmBlog"),
           new MenuDetail("review", "후기관리", "/farmer/myFarmBlog/review"),
           new MenuDetail("sns", "소식관리", "/farmer/myFarmBlog/sns")
        ));
        farmerMenus.put("products", Arrays.asList(
                new MenuDetail("list", "상품리스트", "/farmer/product"),
                new MenuDetail("add", "상품등록", "/farmer/product/add")
        ));
        farmerMenus.put("order", Arrays.asList(
                new MenuDetail("sales", "매출현황", "/farmer/order"),
                new MenuDetail("order", "주문설정", "/farmer/settings")
        ));
    }

    public static List<MenuDetail> getMenus(String code){
        return farmerMenus.get(code);
    }
}
