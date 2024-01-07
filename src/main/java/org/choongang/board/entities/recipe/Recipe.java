package org.choongang.board.entities.recipe;

import lombok.Data;

/**
 * 레시피 모두 보기 test
 * 모든 레시피
 *
 */

@Data
public class Recipe {
    private Long id;
    private String userName;
    private String RcpName;

    public Recipe(String userName, String rcpName) {
        this.userName = userName;
        this.RcpName = rcpName;
    }
}

