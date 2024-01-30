package org.choongang.member.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data

public class RequestNonmember {

    private Long orderNo;
    @NotBlank
    private String orderCellPhone;

}
