package org.choongang.admin.banner.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.banner.entity.Banner;
import org.choongang.admin.banner.entity.BannerGroup;
import org.choongang.admin.banner.service.*;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.MenuDetail;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("adminBannerController")
@RequestMapping("/admin/banner")
@RequiredArgsConstructor
public class BannerController implements ExceptionProcessor {

    private final BannerGroupSaveService bannerGroupSaveService;
    private final BannerGroupDeleteService bannerGroupDeleteService;
    private final BannerInfoService bannerInfoService;
    private final BannerSaveService bannerSaveService;
    private final BannerDeleteService bannerDeleteService;

    private final BannerValidator bannerValidator;
    private final BannerGroupValidator bannerGroupValidator;

    @ModelAttribute("menuCode")
    public String menuCode() {
        return "banner";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> subMenus() {
        return AdminMenu.getMenus("banner");
    }


    @ModelAttribute("bannerGroups")
    public List<BannerGroup> bannerGroups() {
        BannerGroupSearch search = new BannerGroupSearch();
        search.setLimit(10000);
        ListData<BannerGroup> data = bannerInfoService.getGroupList(search);

        return data.getItems();
    }

    /**
     * 배너 그룹 관리
     *
     * @param model
     * @return
     */
    @GetMapping
    public String group(@ModelAttribute  BannerGroupSearch search,  Model model) {
        commonProcess("group", model);

        ListData<BannerGroup> data = bannerInfoService.getGroupList(search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "admin/banner/group";
    }

    /**
     * 그룹 등록
     *
     * @param form
     * @param errors
     * @param model
     * @return
     */
    @PostMapping
    public String addGroup(@Valid RequestBannerGroup form, Errors errors,  Model model) {
        commonProcess("group", model);

        bannerGroupValidator.validate(form, errors);

        if (errors.hasErrors()) {
            String code = errors.getFieldErrors().stream().map(f -> f.getCodes()[0]).findFirst().orElse(null);
            if (code != null) throw new AlertException(Utils.getMessage(code), HttpStatus.BAD_REQUEST);
        }

        bannerGroupSaveService.save(form);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    /**
     * 그룹 목록 수정
     *
     * @param chks
     * @param model
     * @return
     */
    @PatchMapping
    public String editGroup(@RequestParam(name="chk", required = false) List<Integer> chks, Model model) {
        commonProcess("group", model);


        bannerGroupSaveService.saveList(chks);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    /**
     * 그룹 목록 삭제
     *
     * @param chks
     * @param model
     * @return
     */
    @DeleteMapping
    public String deleteGroup(@RequestParam(name="chk", required = false) List<Integer> chks, Model model) {
        commonProcess("group", model);

        bannerGroupDeleteService.deleteList(chks);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    /**
     * 배너 등록
     *
     * @param model
     * @return
     */
    @GetMapping("/group")
    public String add(@ModelAttribute RequestBanner form,  Model model) {
        commonProcess("add", model);

        return "admin/banner/add";
    }

    /**
     * 배너 수정
     * @param seq
     * @param model
     * @return
     */
    @GetMapping("/edit/{seq}")
    public String edit(@PathVariable("seq") Long seq, Model model) {
        commonProcess("edit", model);

        RequestBanner form = bannerInfoService.getForm(seq);
        model.addAttribute("requestBanner", form);

        return "admin/banner/edit";
    }

    /**
     * 배너 추가, 수정
     *
     * @param form
     * @param errors
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid RequestBanner form, Errors errors, Model model) {
        commonProcess(form.getMode(), model);

        bannerValidator.validate(form, errors);

        if (errors.hasErrors()) {
            return "admin/banner/" + form.getMode();
        }

        bannerSaveService.save(form);

        return "redirect:/admin/banner/list/" + form.getGroupCode();
    }

    /**
     * 배너 목록
     *
     * @param groupCode
     * @param model
     * @return
     */
    @GetMapping("/list/{groupCode}")
    public String bannerList(@PathVariable("groupCode") String groupCode, Model model) {
        commonProcess("list", model);

        List<Banner> items = bannerInfoService.getList(groupCode, true);

        model.addAttribute("items", items);

        return "admin/banner/list";
    }

    @PatchMapping("/list")
    public String editBannerList(@RequestParam("chk") List<Integer> chks, Model model) {
        commonProcess("list", model);

        bannerSaveService.saveList(chks);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    @DeleteMapping("/list")
    public String deleteBannerList(@RequestParam("chk") List<Integer> chks, Model model) {
        commonProcess("list", model);

        bannerDeleteService.deleteList(chks);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    /**
     * 배너 공통 처리
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {
        String pageTitle = "배너목록";
        mode = StringUtils.hasText(mode) ? mode : "list";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("group")) {
            pageTitle = "배너그룹";
        } else if (mode.equals("add") || mode.equals("edit")) {
            pageTitle = "배너" + ((mode.equals("edit")) ? "수정" : "등록");
            addCommonScript.add("fileManager");
            addScript.add("banner/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
    }
}