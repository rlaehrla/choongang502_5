package org.choongang.admin.menus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final static Map<String, List<MenuDetail>> menus;

    static {
        menus = new HashMap<>();
        menus.put("config", Arrays.asList(
                new MenuDetail("config", "기본설정", "/admin/config"),
                new MenuDetail("payment.html", "결제설정", "/admin/config/payment")
        ));
        menus.put("member", Arrays.asList(
            new MenuDetail("list", "회원목록", "/admin/member"),
            new MenuDetail("authority", "회원권한", "/admin/member/authority")
        ));
        
        menus.put("board", Arrays.asList(
                new MenuDetail("list", "게시판목록", "/admin/board"),
                new MenuDetail("add", "게시판등록", "/admin/board/add"),
                new MenuDetail("posts", "게시글관리", "/admin/board/posts")
        ));
        menus.put("product", Arrays.asList(
                new MenuDetail("list", "품목리스트", "/admin/product"),
                new MenuDetail("add", "품목등록", "/admin/product/add"),
                new MenuDetail("edit", "품목수정", "/admin/product/edit"),
                new MenuDetail("manage", "품목관리", "/admin/product/manage")
        ));
        menus.put("order", Arrays.asList(
                new MenuDetail("list", "주문목록", "/admin/order"),
                new MenuDetail("setting", "주문설정", "/admin/order/setting")
        ));
        menus.put("customer", Arrays.asList(
                new MenuDetail("list", "상담목록", "/admin/customer"),
                new MenuDetail("add", "상담등록", "/admin/customer/add")
        ));
        menus.put("banner", Arrays.asList(
                new MenuDetail("list", "배너", "/admin/banner"),
                new MenuDetail("add", "배너등록", "/admin/banner/add")
        ));
        menus.put("search", Arrays.asList(
                new MenuDetail("list", "검색순위", "/admin/search")
        ));


    }

    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }
}
