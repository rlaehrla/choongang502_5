package org.choongang.admin.product.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.product.repositories.CategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CategoryValidator implements Validator {
    private final CategoryRepository repository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestCategory.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /* cateCd 중복 여부 체크 */
        RequestCategory form = (RequestCategory) target;
        String cateCd = form.getCateCd();

        if(StringUtils.hasText(cateCd) && repository.existsById(cateCd)){
            errors.rejectValue("cateCd", "Duplicated");
        }



    }
}

