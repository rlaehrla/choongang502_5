package org.choongang.recipe.services;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class RecipeWishNotFoundException extends AlertBackException {

    public RecipeWishNotFoundException(){
        super("레시피 찜을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
