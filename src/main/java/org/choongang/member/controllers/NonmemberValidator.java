package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

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
        Long orderNo = Objects.requireNonNullElse(form.getOrderNo(), 0L);

        if(orderNo <= 0){
            errors.rejectValue("orderNo", "NotZero");
        }
    }
}
