package org.choongang.admin.banner.service;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class BannerGroupNotFoundException extends AlertBackException {
    public BannerGroupNotFoundException() {
        super(Utils.getMessage("NotFound.bannerGroup", "errors"), HttpStatus.NOT_FOUND);
    }
}