package org.choongang.recipe.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 레시피 모두 보기 test
 * 모든 레시피
 *
 */

@Data
@NoArgsConstructor
public class Recipe {
    private Long id;
    private String userName;
    private String rcpName;



    public Recipe(String userName, String rcpName) {
        this.userName = userName;
        this.rcpName = rcpName;
    }
}

