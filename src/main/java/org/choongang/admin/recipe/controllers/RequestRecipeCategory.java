package org.choongang.admin.recipe.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestRecipeCategory {

    @NotBlank
    private String cateCd;

    @NotBlank
    private String cateNm;
}
