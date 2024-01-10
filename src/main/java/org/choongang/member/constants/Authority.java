package org.choongang.member.constants;

import java.util.Arrays;
import java.util.List;

public enum Authority {
    ALL("전체"),
    USER("일반"), // 일반 사용자
    FARMER("농부"),    // 농장주(판매자)
    MANAGER("부관리자"), // 부 관리자
    ADMIN("관리자"); // 최고 관리자

    private final String title;

    Authority(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    /**
     * 0 - 관리할 authority code
     * 1 - authority code에 대한 명칭 부여
     * @return
     */
    public static List<String[]> getList(){

        return Arrays.asList(
                new String[]{ALL.name(), ALL.title},
                new String[]{USER.name(), USER.title},
                new String[]{FARMER.name(), FARMER.title},
                new String[]{MANAGER.name(), MANAGER.title},
                new String[]{ADMIN.name(), ADMIN.title}

        );

    }

}
