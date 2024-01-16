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

    private Long farmerSeq; // 농부 시퀀스

    private int listOrder; // 진열가중치 - 내림차순 정렬 우선순위
    private boolean active; // 사용여부
}
