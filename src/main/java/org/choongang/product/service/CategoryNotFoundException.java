package org.choongang.product.service;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends AlertBackException {

    public CategoryNotFoundException(){
        super(Utils.getMessage("NotFound.product.category", "errors"), HttpStatus.NOT_FOUND);
    }
}
