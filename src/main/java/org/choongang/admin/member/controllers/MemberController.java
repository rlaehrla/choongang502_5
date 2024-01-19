package org.choongang.admin.member.controllers;

import jakarta.mail.internet.AddressException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.member.service.AddressBadException;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.AddressAssist;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.MenuDetail;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.member.service.AddressSaveService;
import org.choongang.member.service.MemberEditService;
import org.choongang.member.service.MemberInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private final AddressRepository addressRepository;
    private final AddressValidator addressValidator;
    private final AddressSaveService addressSaveService;

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

    @GetMapping("/address/{seq}")
    public String address(@PathVariable("seq") Long seq, Model model){
        Address address = addressRepository.findById(seq).orElse(null);

        AddressAssist addr = new AddressAssist();
        addr.setAddress(address.getAddress());
        addr.setAddressSub(address.getAddressSub());
        addr.setDefaultAddress(address.isDefaultAddress());
        addr.setZoneCode(address.getZoneCode());
        addr.setTitle(address.getTitle());

        RequestAddress form = new RequestAddress();
        form.setAddr(addr);
        form.setMemberSeq(address.getMember().getSeq());
        form.setSeq(seq);

        List<String> addCommonScript = new ArrayList<>();
        addCommonScript.add("address");

        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("requestAddress", form);
        return "admin/member/edit_address";
    }

    @PostMapping("/address/save")
    public String addrPs(RequestAddress form, Errors errors, Model model ){

        addressValidator.validate(form, errors);

        if (errors.hasErrors()){
            throw new AddressBadException();
        }

        addressSaveService.edit(form.getSeq(), form.getAddr());

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "회원 목록";

        List<String> addCommonScript = new ArrayList<>();

        if(mode.equals("authority")){
            pageTitle = "회원 권한";
        } else if (model.equals("edit")) {
            pageTitle = "회원정보 수정";
            addCommonScript.add("layer");
        }

        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }


}
