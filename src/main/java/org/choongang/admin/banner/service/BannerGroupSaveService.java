package org.choongang.admin.banner.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.banner.controllers.RequestBannerGroup;
import org.choongang.admin.banner.entity.BannerGroup;
import org.choongang.admin.banner.repository.BannerGroupRepository;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerGroupSaveService {
    private final BannerGroupRepository groupRepository;
    private final Utils utils;

    /**
     * 배너 그룹 추가 & 수정
     *
     * @param form
     */
    public void save(RequestBannerGroup form) {
        String groupCode = form.getGroupCode();

        BannerGroup data = groupRepository.findById(groupCode).orElseGet(() -> {
            BannerGroup bannerGroup = new BannerGroup();
            bannerGroup.setGroupCode(form.getGroupCode());
            return bannerGroup;
        });

        data.setGroupName(form.getGroupName());
        data.setActive(form.isActive());

        groupRepository.saveAndFlush(data);
    }

    /**
     * 배너 그룹 목록 수정
     *
     * @param chks
     */
    public void saveList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("수정할 배너 그룹을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            String groupCode = utils.getParam("groupCode_" + chk);
            BannerGroup bannerGroup = groupRepository.findById(groupCode).orElse(null);
            if (bannerGroup == null) continue;

            bannerGroup.setGroupName(utils.getParam("groupName_" + chk));
            bannerGroup.setActive(Boolean.parseBoolean(utils.getParam("active_" + chk)));
        }

        groupRepository.flush();
    }
}