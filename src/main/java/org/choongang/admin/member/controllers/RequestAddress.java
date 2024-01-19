package org.choongang.admin.member.controllers;

import lombok.Data;
import org.choongang.commons.AddressAssist;

@Data
public class RequestAddress {

    private Long seq;

    private AddressAssist addr;

}
