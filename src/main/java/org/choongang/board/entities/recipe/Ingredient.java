package org.choongang.board.entities.recipe;

import lombok.Data;

/**
 * 레시피 모두 보기 test
 * 모든 재료
 *
 */

@Data
public class Ingredient {
    private Long id;
    private String IngName;

    public Ingredient(String ingName) {
        IngName = ingName;
    }
}

