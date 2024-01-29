package org.choongang.admin.banner.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestBannerGroup {
    @NotBlank
    private String groupCode;

    @NotBlank
    private String groupName;

    private boolean active;
}