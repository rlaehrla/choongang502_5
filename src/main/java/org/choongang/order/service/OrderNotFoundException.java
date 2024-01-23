package org.choongang.order.service;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends AlertBackException {

    public OrderNotFoundException(){
        super(Utils.getMessage("NotFound.order", "errors"), HttpStatus.NOT_FOUND);
    }
}
