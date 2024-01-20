package org.choongang.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.board.controllers.BoardDataSearch;
import org.choongang.board.entities.Board;
import org.choongang.board.entities.BoardData;
import org.choongang.board.service.BoardInfoService;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;
import org.choongang.farmer.blog.intro.BlogIntroPost;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/farm/blog")
@RequiredArgsConstructor
public class BlogController {

    private final Utils utils;
    private final HttpServletRequest request ;
    private final MemberUtil memberUtil ;
    private final ConfigInfoService infoService;

    private final BoardInfoService boardInfoService;
    private final BoardConfigInfoService configInfoService; // 게시판 설정 조회 서비스
    private Board board; // 게시판 설정

    /**
     * 블로그 메인페이지
     */
    @GetMapping("/{farmerId}")
    public String blog(@PathVariable("farmerId") String farmerId, Model model){

        commonProcess("main", model);

        model.addAttribute("farmerId", farmerId) ;

        return utils.tpl("blog/farmerblog");
    }

    @GetMapping("/sales")
    public String sales(){

        return utils.tpl("blog/_farmSales");
    }

    /**
     * 소개글 탭
     */
    @GetMapping("/intro/{farmerId}")
    public String intro(@PathVariable("farmerId") String farmerId, Model model){

        BlogIntroPost intro = infoService.get(farmerId, BlogIntroPost.class).orElseGet(BlogIntroPost::new);
        model.addAttribute("blogIntroPost", intro);

        return utils.tpl("blog/_farmIntroduction");
    }
    @GetMapping("/review")
    public String review(){

        return utils.tpl("blog/_farmReview");
    }

    /**
     * 블로그 소식글 탭
     */
    @GetMapping("/sns/{farmerId}")
    public String sns(@PathVariable String farmerId, @ModelAttribute BoardDataSearch search, Model model){
        //commonProcess("sns", model);

        String bid = "farmSns" ;
        search.setUserId(farmerId);
        ListData<BoardData> data = boardInfoService.getList(bid, search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("blog/_farmSns");
    }

    /**
     * 공통 기능
     */
    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "main");
        String pageTitle = "농부 블로그";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCommonCss = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        addCommonScript.add("tab");
        addCommonCss.add("tab");

        if (mode.equals("sns")) {
            addScript.add("board/form");
            /* 게시판 설정 처리 S */

            board = configInfoService.get("farmSns"); // 매번 DB조회

            // 스킨별 css, js 추가
            String skin = board.getSkin();
            addCss.add("board/skin_" + skin);
            addScript.add("board/skin_" + skin);

            model.addAttribute("board", board);

            pageTitle = board.getBName(); // 게시판명이 기본 타이틀
            model.addAttribute("pageTitle", pageTitle);

            /* 게시판 설정 처리 E */
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript) ;
        model.addAttribute("addScript", addScript) ;
        model.addAttribute("addCommonCss", addCommonCss) ;
        model.addAttribute("addCss", addCss) ;
    }
}
