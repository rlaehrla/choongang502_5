package org.choongang.admin.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.AdminMenu;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.commons.MenuDetail;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.service.MemberInfo;
import org.choongang.member.service.MemberInfoService;
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

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") String userId, Model model) {
        MemberInfo memberInfo = (MemberInfo)infoService.findByUserId(userId);
        AbstractMember member = memberInfo.getMember();
        List<Address> addressList = member.getAddress();

        model.addAttribute("member", member);
        model.addAttribute("address", addressList);

        return "admin/member/edit";
    }

    @PostMapping("/save")
    public String save(@Valid MemberForm form, Errors errors, Model model) {



        return "redirect:/admin/member";
    }


    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "회원 목록";

        if(mode.equals("authority")){
            pageTitle = "회원 권한";
        }


        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }
}
