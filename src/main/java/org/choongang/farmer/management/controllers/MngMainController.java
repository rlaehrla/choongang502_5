package org.choongang.farmer.management.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/farmer")
@RequiredArgsConstructor
public class MngMainController implements ExceptionProcessor {

    private final MemberUtil memberUtil ;

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "dashboard";
    }

    /**
     * 판매자 관리페이지의 메인(대시보드 뷰)으로 이동
     */
    @GetMapping
    public String goIndex(Model model) {
        model.addAttribute("pageTitle", "관리페이지") ;

        return "admin/farmer/main/dashboard" ;
    }
}
