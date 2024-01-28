package org.choongang.admin.recipe.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestRecipeCategory {
    @NotBlank
    private String category;

    @NotBlank
    private String subCategory;
}
