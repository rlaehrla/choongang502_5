package org.choongang.member.controllers;


import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController { //implements ExceptionProcessor {
    private final Utils utils;
    private final MemberUtil memberUtil;

    @GetMapping
    public String myPage(Model model) {
        commonProcess("myPage", model);
        return utils.tpl("member/my_diary/my_page");
    }



    /**
     * 마이 다이어리
     * @param model
     * @return
     */
    @GetMapping("/diary")
    public String diary(@ModelAttribute RequestMemberInfo form, Model model, Errors errors) {
        commonProcess("diary", model);
        if(memberUtil.isLogin()) {
            AbstractMember member = memberUtil.getMember();
            form.setNickname(member.getNickname());
            // 프로필 메세지 불러오기
            // 프로필 사진 불러오기
            // 북마크 불러오기
            // 레시피 불러오기
            // 레시피 작성으로 이동하는 버튼 필요

        }

        return utils.tpl("member/my_diary/my_diary");
    }

    @GetMapping("/diary/content/{num}")
    public String content(@PathVariable("num") String num) {
        return utils.tpl("member/my_diary/content/" + num);
    }


    /**
     * 전체 주문내역
     * @param model
     * @return
     */
    @GetMapping("/allorders")
    public String allorders(Model model) {
        commonProcess("allorders", model);
        return utils.tpl("member/my_diary/my_allorders");
    }

    /**
     * 마이 포인트
     * @param model
     * @return
     */
    @GetMapping("/point")
    public String point(Model model) {
        commonProcess("point", model);
        return utils.tpl("member/my_diary/my_point");
    }

    /**
     * 회원 정보
     * @param model
     * @return
     */
    @GetMapping("/info")
    public String info(@ModelAttribute RequestMemberInfo form, Model model, Errors errors) {
        commonProcess("info", model);
        if(memberUtil.isLogin()) {
            AbstractMember member = memberUtil.getMember();
            form.setUserId(member.getUserId());
            form.setEmail(member.getEmail());
            form.setNickname(member.getNickname());
            form.setUsername(member.getUsername());
            form.setProfileImage(member.getProfileImage());
        }
        return utils.tpl("member/my_diary/my_info");
    }
    @PostMapping("/info")
    public String infoPs(@ModelAttribute RequestMemberInfo form, Model model, Errors errors) {
        commonProcess("info", model);

        return utils.tpl("member/my_diary/my_info");
    }

    /**
     * 최근 본 상품
     * @param model
     * @return
     */
    @GetMapping("/recentlyview")
    public String recentlyview(Model model) {
        commonProcess("recentlyview", model);
        return utils.tpl("member/my_diary/my_recently_view");
    }



    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");



        List<String> addCommonScript = new ArrayList<>();    // 공통 자바스크립트
        List<String> addCommonCss = new ArrayList<>();    // 공통 자바스크립트
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();    // 프론트 자바스크립트

        addCommonScript.add("tab");
        addCommonCss.add("tab");

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
        model.addAttribute("addCommonCss", addCommonCss);
    }


}
