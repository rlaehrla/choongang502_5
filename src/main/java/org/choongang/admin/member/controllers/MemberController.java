package org.choongang.admin.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.MenuDetail;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.member.constants.Authority;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.Authorities;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.member.service.MemberEditService;
import org.choongang.member.service.MemberInfo;
import org.choongang.member.service.MemberInfoService;
import org.choongang.member.service.MemberNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    private final MemberInfoService infoService;
    private final MemberRepository memberRepository;
    private final MemberFormValidator validator;
    private final MemberEditService editService;

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "member";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {

        return AdminMenu.getMenus("member");
    }

    @GetMapping
    public String list(@ModelAttribute MemberSearch search, Model model) {
        commonProcess("list", model);

        if (search != null && StringUtils.hasText(search.getSopt()) && StringUtils.hasText(search.getSkey())) {
            // 검색 조건이 존재하면 검색 수행
            List<AbstractMember> searchResults = infoService.searchMembers(search);
            model.addAttribute("items", searchResults);
        } else {
            // 검색 조건이 없으면 전체 목록 조회
            ListData<AbstractMember> data = infoService.getList(search);


            model.addAttribute("items", data.getItems()); // 목록
            model.addAttribute("pagination", data.getPagination()); // 페이징
        }

        return "admin/member/list";
    }

    @GetMapping("/edit/{seq}")
    public String edit(@PathVariable("seq") Long seq, @ModelAttribute MemberForm member, Model model) {
        commonProcess("edit", model);

        member = infoService.getMemberForm(seq);

        member.setAuthorities(member.getAuthorities());
        model.addAttribute("member", member);
        return "admin/member/edit";
    }

    @PostMapping("/save")
    public String save(@Valid MemberForm form, Errors errors, Model model) {
        String mode = form.getMode();
        commonProcess(mode, model);
        validator.validate(form, errors);

        if(errors.hasErrors()){

            throw new AlertBackException("올바르지 않은 형식입니다", HttpStatus.BAD_REQUEST);
        }

        editService.editMember(form);

        return "redirect:/admin/member";
    }


    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "회원 목록";

        if(mode.equals("authority")){
            pageTitle = "회원 권한";
        } else if (model.equals("edit")) {
            pageTitle = "회원정보 수정";
        }


        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }


}
