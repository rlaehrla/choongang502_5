package org.choongang.member.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.validators.PasswordValidator;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class InfoSaveValidator implements Validator, PasswordValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestMemberInfo.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         * [공통 validator]
         * 비밀번호 복잡성 체크
         * 비밀번호, 비밀번호 확인 일치 여부 체크
         */

        RequestMemberInfo form = (RequestMemberInfo)target;
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        if (StringUtils.hasText(password)) {
            // 비밀번호 최소 자리수 체크
            if (password.length() < 8) {
                errors.rejectValue("password", "Size");
            }

            // 비밀번호 복잡성 체크 - 대소문자 1개 각각 포함, 숫자 1개 이상 포함, 특수문자도 1개 이상 포함
            if (!alphaCheck(password, true) || !numberCheck(password) || !specialCharsCheck(password)) {
                errors.rejectValue("password", "Complexity");
            }

            // 비밀번호, 비밀번호 확인 일치 여부 체크
            if (StringUtils.hasText(confirmPassword)
                    && !password.equals(confirmPassword)) {
                errors.rejectValue("confirmPassword", "Mismatch.password");
            }
        }
        /**
         * [농장주인일 때만 필요한 validator]
         * 농장이름 필수 체크
         */
        // mType값이 "F"인지 체크
        String mType = form.getMType() ;
        if (StringUtils.hasText(mType) && mType.equals("F")) {
            String businessPermitNum = form.getBusinessPermitNum() ;
            String farmTitle = form.getFarmTitle() ;

            // 농장이름 필수 체크
            if (!StringUtils.hasText(farmTitle) || farmTitle.isBlank()) {
                errors.rejectValue("farmTitle", "NotBlank");
            }
        }
    }
}
