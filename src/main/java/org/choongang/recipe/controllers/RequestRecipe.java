package org.choongang.recipe.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestRecipe {
    private String mode = "add";
    private Long seq; // 게시글 번호
    private String gid = UUID.randomUUID().toString();

/*    @NotBlank
    private String poster; // 글 작성자*/

    @NotBlank
    private String rcpName; // 글 제목
    private String rcpInfo; // 글 소개

    //@NotBlank
    private int estimatedT;

    private String category;
    private String subCategory;
    private int amount;

    private String requiredIng;
    private String subIng;
    private String condiments;







}
