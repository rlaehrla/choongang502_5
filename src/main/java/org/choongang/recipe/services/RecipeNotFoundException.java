package org.choongang.recipe.services;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class RecipeNotFoundException extends AlertBackException {
    public RecipeNotFoundException() {
        super(Utils.getMessage("NotFound.recipe", "errors"), HttpStatus.NOT_FOUND);
    }
}
