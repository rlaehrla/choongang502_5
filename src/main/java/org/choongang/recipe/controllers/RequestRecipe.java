package org.choongang.recipe.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestRecipe {
    private String mode = "write";
    private Long seq; // 게시글 번호
    private String rid; // 레시피 ID
    private String gid = UUID.randomUUID().toString();

    @NotBlank
    private String poster; // 글 작성자

    @NotBlank
    private String rcpName; // 글 제목

    private String rcpInfo; // 글 소개







}
