package org.choongang.admin.banner.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.banner.entity.Banner;
import org.choongang.admin.banner.repository.BannerRepository;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BannerDeleteService {
    private final BannerInfoService bannerInfoService;
    private final BannerRepository bannerRepository;
    private final FileDeleteService fileDeleteService;
    private final Utils utils;

    public void delete(Long seq) {
        Banner banner = bannerInfoService.get(seq);

        FileInfo fileInfo = banner.getBannerImage();
        if (fileInfo != null) {
            fileDeleteService.delete(fileInfo.getSeq());
        }

        bannerRepository.delete(banner);
        bannerRepository.flush();
    }

    public void deleteList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("삭제할 배너를 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            Long seq = Long.valueOf(utils.getParam("seq_" + chk));
            delete(seq);
        }
    }
}