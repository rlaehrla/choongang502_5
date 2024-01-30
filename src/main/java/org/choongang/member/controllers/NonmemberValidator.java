package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
@RequiredArgsConstructor
public class NonmemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestNonmember.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // 주문번호는 0 초과 (음수, 0 x)
        RequestNonmember form = (RequestNonmember) target;
        Long orderNo = form.getOrderNo();

        if(orderNo <= 0){
            errors.rejectValue("orderNo", "NotZero");
        }
    }
}
