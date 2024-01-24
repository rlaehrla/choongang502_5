package org.choongang.product.constants;

import lombok.Getter;

public enum ProductStatus {
    SALE("판매중"), // 판매중
    OUT_OF_STOCK("품절"), // 품절
    PREPARE("상품 준비중") ; // 상품 준비중

    @Getter
    private final String description ;

    ProductStatus(String description) {
        this.description = description ;
    }
}
