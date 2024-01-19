package org.choongang.admin.member.controllers;

import lombok.Data;
import org.choongang.commons.AddressAssist;

@Data
public class RequestAddress {

    private Long seq; // 주소 seq

    private Long memberSeq; // 회원 seq

    private AddressAssist addr;

}
