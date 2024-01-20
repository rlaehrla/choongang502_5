package org.choongang.order.service;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class OrderBadException extends AlertBackException {

    public OrderBadException(){
        super("올바르지 않은 주문 요청입니다.", HttpStatus.BAD_REQUEST);
    }
}
