package org.choongang.admin.product.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.product.constants.DiscountType;
import org.choongang.product.entities.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestProduct.class);
    }

    /**
     * 1. 상품할인이 %면 할인가 100% 이하로만 가능하게
     * 2. 상품할인이 원이면 판매가 이하로만
     *
     * @param target the object that is to be validated
     * @param errors contextual state about the validation process
     */
    @Override
    public void validate(Object target, Errors errors) {
        RequestProduct form = (RequestProduct) target;

        if(form.getDiscountType() == DiscountType.PERCENT
        && form.getDiscount() > 100){
            errors.rejectValue("discount", "PercentOver");
        }else if(form.getDiscountType() == DiscountType.PRICE
                && form.getDiscount() > form.getSalePrice()){
            errors.rejectValue("discount", "PriceOver");
        }
    }
}
