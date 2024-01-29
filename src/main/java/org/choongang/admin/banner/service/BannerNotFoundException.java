package org.choongang.admin.banner.service;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class BannerNotFoundException extends AlertBackException {
    public BannerNotFoundException() {
        super(Utils.getMessage("NotFound.banner", "errors"), HttpStatus.NOT_FOUND);
    }
}