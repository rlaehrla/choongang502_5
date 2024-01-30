package org.choongang.admin.banner.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.banner.controllers.RequestBanner;
import org.choongang.admin.banner.entity.Banner;
import org.choongang.admin.banner.entity.BannerGroup;
import org.choongang.admin.banner.repository.BannerGroupRepository;
import org.choongang.admin.banner.repository.BannerRepository;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.file.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BannerSaveService {
    private final BannerGroupRepository groupRepository;
    private final BannerRepository repository;
    private final FileUploadService uploadService;
    private final Utils utils;

    public void save(RequestBanner form) {
        String mode = form.getMode();
        Long seq = form.getSeq();

        mode = StringUtils.hasText(mode) ? mode : "add";

        Banner banner = null;
        if (mode.equals("edit") && seq != null) {
            banner = repository.findById(seq).orElseThrow(BannerNotFoundException::new);
        } else {
            banner = new Banner();
            BannerGroup bannerGroup = groupRepository.findById(form.getGroupCode()).orElseThrow(BannerGroupNotFoundException::new);
            banner.setBannerGroup(bannerGroup);
        }

        banner.setBName(form.getBName());
        banner.setListOrder(form.getListOrder());
        banner.setActive(form.isActive());
        banner.setBLink(form.getBLink());
        banner.setTarget(form.getTarget());

        repository.save(banner);

        String groupCode = banner.getBannerGroup().getGroupCode();
        if (form.getFiles() != null && !form.getFiles()[0].isEmpty()) {
            try {
                uploadService.upload(form.getFiles(), groupCode, "banner_" + banner.getSeq(), true, true);
                uploadService.processDone(groupCode);
            } catch (Exception e) { }
        }
    }

    public void saveList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("수정할 배너를 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            Long seq = Long.valueOf(utils.getParam("seq_" + chk));
            boolean active = Boolean.parseBoolean(utils.getParam("active_" + chk));

            Banner banner = repository.findById(seq).orElse(null);
            if (banner == null) continue;

            banner.setActive(active);
        }

        repository.flush();
    }
}