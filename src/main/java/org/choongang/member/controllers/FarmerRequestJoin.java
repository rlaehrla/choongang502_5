package org.choongang.member.controllers;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class FarmerRequestJoin {

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
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "{phone.number.invalid}")
    private String tel ;

    @NotBlank
    @Size(min=2)
    private String farmTitle ;

    @NotBlank
    private String farmAddress ;

    @NotBlank
    @Size(min = 10, max = 10)
    private String certificateNo ;

    @AssertTrue
    private boolean agree;

}
