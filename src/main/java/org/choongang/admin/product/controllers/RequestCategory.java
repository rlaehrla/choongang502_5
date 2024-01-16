package org.choongang.admin.product.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.product.constants.MainCategory;

@Data
public class RequestCategory {

    @NotBlank
    private String cateCd; // 분류 코드

    @NotBlank
    private String mainCategory; // 대분류코드

    @NotBlank
    private String cateNm; // 분류명
}
