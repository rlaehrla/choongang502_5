package org.choongang.member.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.service.FindPwService;
import org.choongang.member.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes({"EmailAuthVerified", "BusinessNoVerified"})    // model에 같은 속성명의 값이 있으면 세션에 저장하여 유지됨
public class MemberController implements ExceptionProcessor {

    private final Utils utils;
    private final JoinService joinService;
    private final FindPwService findPwService ;
    private final HttpSession session ;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {
        commonProcess("join", model);

        String mType = StringUtils.hasText(form.getMtype()) ? form.getMtype() : "M" ;
        session.setAttribute("mType", mType);    // 세션에 mType 값 저장

        // 이메일 인증과 사업자등록 번호 인증 여부 false --> 초기화
        model.addAttribute("EmailAuthVerified", false);
        model.addAttribute("BusinessNoVerified", false) ;

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model, SessionStatus sessionStatus) {
        commonProcess("join", model);

        joinService.process(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        // 세션값 비우기 --> 이메일 인증과 사업자등록번호 인증 초기화
        sessionStatus.setComplete();

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        commonProcess("login", model);

        return utils.tpl("member/login");
    }



    /**
     * 비밀번호 찾기 양식
     */
    @GetMapping("/find_pw")
    public String findPw(@ModelAttribute RequestFindPw form, Model model) {
        commonProcess("find_pw", model);

        return utils.tpl("member/find_pw");
    }

    /**
     * 비밀번호 찾기 처리
     */
    @PostMapping("/find_pw")
    public String findPwPs(@Valid RequestFindPw form, Errors errors, Model model) {
        commonProcess("find_pw", model);

        findPwService.process(form, errors); // 비밀번호 찾기 처리

        if (errors.hasErrors()) {
            return utils.tpl("member/find_pw");
        }

        // 비밀번호 찾기에 이상 없다면 완료 페이지로 이동
        return "redirect:/member/find_pw_done";
    }

    /**
     * 비밀번호 찾기 완료 페이지
     *
     * @param model
     * @return
     */
    @GetMapping("/find_pw_done")
    public String findPwDone(Model model) {
        commonProcess("find_pw", model);

        return utils.tpl("member/find_pw_done");
    }

    /**
     * 장바구니 이동 페이지
     *
     * @param model
     * @return
     */
    @GetMapping("/cart")
    public String cart(Model model){
        commonProcess("cart", model);

        return utils.tpl("member/cart");

    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");

        List<String> addCommonScript = new ArrayList<>();    // 공통 자바스크립트
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();    // 프론트 자바스크립트

        if (mode.equals("login")) { // 로그인
            pageTitle = Utils.getMessage("로그인", "commons");
        } else if (mode.equals("join")) { // 회원가입
            // 공통 JS
            addCommonScript.add("address");
            addCommonScript.add("fileManager");
            // 회원가입 페이지를 위한 CSS, JS
            addScript.add("member/join");
            addScript.add("member/form");

        } else if (mode.equals("find_pw")) { // 비밀번호 찾기
            pageTitle = Utils.getMessage("비밀번호_찾기", "commons");
        } else if (mode.equals("cart")) {
            // 장바구니
            pageTitle = Utils.getMessage("장바구니", "commons");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);
    }



}