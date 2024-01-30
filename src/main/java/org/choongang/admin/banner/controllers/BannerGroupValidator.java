package org.choongang.admin.banner.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.banner.repository.BannerGroupRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class BannerGroupValidator implements Validator {

    private final BannerGroupRepository bannerGroupRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestBannerGroup.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestBannerGroup form = (RequestBannerGroup) target;

        /**
         * 배너 코드 중복 등록 여부 체크
         */
        String groupCode = form.getGroupCode();
        if (StringUtils.hasText(groupCode) && bannerGroupRepository.existsById(groupCode)) {
            errors.rejectValue("groupCode", "Duplicated");
        }
    }
}