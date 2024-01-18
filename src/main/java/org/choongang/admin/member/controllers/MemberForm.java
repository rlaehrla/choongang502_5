package org.choongang.admin.member.controllers;

import lombok.Data;
import org.choongang.member.constants.Authority;
import org.choongang.member.entities.Address;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberForm {
    private String mode = "edit";

    private boolean activity;

    private Long seq;
    private String gid;
    private String userId;
    private String username;
    private String nickname;
    private String email;
    private String tel;
    private String password;

    private List<Address> address = new ArrayList<>();
    private List<String> authorities = new ArrayList<>();
}
