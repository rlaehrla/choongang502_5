package org.choongang.product.service;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends AlertException {

    public ProductNotFoundException(){
        super(Utils.getMessage("NotFound.product", "errors"), HttpStatus.NOT_FOUND);
    }
}
