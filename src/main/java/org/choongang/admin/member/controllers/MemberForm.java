package org.choongang.admin.member.controllers;

import lombok.Data;

@Data
public class MemberForm {
    private String mode = "edit";
    private Long seq;
    private String password;
    private String confirmPassword;

    private String zoneCode;
    private String address;
    private String addressSub;
}
