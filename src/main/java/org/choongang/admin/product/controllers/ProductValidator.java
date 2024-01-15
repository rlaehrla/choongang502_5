package org.choongang.admin.product.controllers;

import lombok.RequiredArgsConstructor;
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

    @Override
    public void validate(Object target, Errors errors) {

    }
}
