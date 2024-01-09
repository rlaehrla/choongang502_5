package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.validators.PasswordValidator;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FarmerJoinValidator implements Validator, PasswordValidator {

    private final FarmerRepository farmerRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(FarmerRequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         * 1. 이메일, 아이디 중복 여부 체크
         * 2. 비밀번호 복잡성 체크 - 대소문자 1개 각각 포함, 숫자 1개 이상 포함, 특수문자도 1개 이상 포함
         * 3. 비밀번호, 비밀번호 확인 일치 여부 체크
         */

        FarmerRequestJoin form = (FarmerRequestJoin) target;
        String email = form.getEmail();
        String userId = form.getUserId();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        String certificateNo = form.getCertificateNo() ;

        // 1. 이메일, 아이디 중복 여부 체크
        if (StringUtils.hasText(email) && farmerRepository.existsByEmail(email)) {
            errors.rejectValue("email", "Duplicated");
        }

        if (StringUtils.hasText(userId) && farmerRepository.existsByUserId(userId)) {
            errors.rejectValue("userId", "Duplicated");
        }

        // 2. 비밀번호 복잡성 체크 - 대소문자 1개 각각 포함, 숫자 1개 이상 포함, 특수문자도 1개 이상 포함
        if (StringUtils.hasText(password) &&
                (!alphaCheck(password, true) || !numberCheck(password) || !specialCharsCheck(password))) {
            errors.rejectValue("password", "Complexity");
        }

        // 3. 비밀번호, 비밀번호 확인 일치 여부 체크
        if (StringUtils.hasText(password) && StringUtils.hasText(confirmPassword)
            && !password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "Mismatch.password");
        }
    }
}
