package org.choongang.admin.banner.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.banner.entity.Banner;
import org.choongang.admin.banner.controllers.RequestBanner;
import org.choongang.admin.banner.repository.BannerRepository;
import org.choongang.file.service.FileUploadService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerSaveService {

    private final BannerRepository repository;
    private final FileUploadService fileUploadService;


    public Banner save(RequestBanner form) {
        String mode = form.getMode();
        Banner banner = null;

            banner = new Banner();
            banner.setGid(form.getGid());
            banner.setHorizontal(form.getHorizontal());
            banner.setVertical(form.getVertical());
            banner.setBannerWhere(form.getBannerWhere());


        repository.saveAndFlush(banner);
        fileUploadService.processDone(banner.getGid());

        return banner;
    }
}
