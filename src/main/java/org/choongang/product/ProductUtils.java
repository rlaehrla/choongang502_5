package org.choongang.product;

import org.choongang.product.constants.DiscountType;
import org.springframework.stereotype.Component;

@Component
public class ProductUtils {

    /**
     * 총 가격 계산
     * @param salePrice : 판매가
     * @param discountType : 할인 타입
     * @param discount : 할인율/할인가격
     * @param deliveryPrice : 배송비
     * @return
     */
    public int calTotal(int salePrice, DiscountType discountType, int discount, int deliveryPrice){

        if(discount != 0){
            if(discountType == DiscountType.PRICE){
                return salePrice - discount + deliveryPrice;
            } else{
                return (int) Math.round(salePrice * (discount * 0.01)) + deliveryPrice;
            }
        }else {
            return salePrice + deliveryPrice;
        }

    }



}
