package org.choongang.product.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MainCategory {
    GRAIN("곡물"),
    VEGETABLE("채소"),
    FRUIT("과일");

    private String title;

    MainCategory(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public static List<String[]> getList(){
        return Arrays.asList(
                new String[]{GRAIN.name(), GRAIN.getTitle()},
                new String[]{VEGETABLE.name(), VEGETABLE.getTitle()},
                new String[]{FRUIT.name(), FRUIT.getTitle()}

        );

    }

}
