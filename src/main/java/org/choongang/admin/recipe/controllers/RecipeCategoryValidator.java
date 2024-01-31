package org.choongang.admin.recipe.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RecipeCategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestRecipeCategory.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
