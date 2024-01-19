package org.choongang.admin.member.service;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class AddressBadException extends AlertBackException {

    public AddressBadException(){
        super("올바르지 않은 주소입니다.", HttpStatus.BAD_REQUEST);
    }
}
