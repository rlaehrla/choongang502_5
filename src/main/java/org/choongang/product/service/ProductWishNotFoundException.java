package org.choongang.product.service;

import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class ProductWishNotFoundException extends AlertBackException {

    public ProductWishNotFoundException(){

        super("상품 찜을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
