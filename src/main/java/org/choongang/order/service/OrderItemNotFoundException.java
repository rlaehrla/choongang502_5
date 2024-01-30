package org.choongang.order.service;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class OrderItemNotFoundException extends AlertBackException {
    public OrderItemNotFoundException() {
        super(Utils.getMessage("NotFound.orderItem", "errors"), HttpStatus.NOT_FOUND);
    }
}
