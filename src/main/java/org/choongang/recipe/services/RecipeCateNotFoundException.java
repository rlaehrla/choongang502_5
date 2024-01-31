package org.choongang.recipe.services;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class RecipeCateNotFoundException extends AlertBackException {

    public RecipeCateNotFoundException(){
        super("레시피 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
