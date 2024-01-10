package org.choongang.member.controllers;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestJoin {

    private String gid = UUID.randomUUID().toString();

    @NotBlank @Email
    private String email;

    @NotBlank
    @Size(min=6)
    private String userId;

    @NotBlank
    @Size(min=8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String name;

    @NotNull
    private String tel;

    @AssertTrue
    private boolean agree;

}
