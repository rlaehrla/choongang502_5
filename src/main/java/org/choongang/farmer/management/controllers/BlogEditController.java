package org.choongang.farmer.management.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.admin.config.service.ConfigSaveService;
import org.choongang.board.controllers.BoardDataSearch;
import org.choongang.board.entities.Board;
import org.choongang.board.entities.BoardData;
import org.choongang.board.service.BoardInfoService;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.commons.ListData;
import org.choongang.commons.MenuDetail;
import org.choongang.commons.Utils;
import org.choongang.farmer.blog.intro.BlogIntroPost;
import org.choongang.farmer.management.menus.FarmerMenu;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/farmer/myFarmBlog")
@RequiredArgsConstructor
public class BlogEditController {

    private final ConfigInfoService infoService;
    private final MemberUtil memberUtil ;
    private final ConfigSaveService saveService ;
    private final FileUploadService fileUploadService ;

    private final BoardInfoService boardInfoService;
    private final BoardConfigInfoService configInfoService; // 게시판 설정 조회 서비스
    private Board board; // 게시판 설정

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "blog";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {

        return FarmerMenu.getMenus("blog");
    }

    /**
     * 소개글 관리
     */
    @GetMapping
    public String blogIntro(@ModelAttribute BlogIntroPost intro,
                            @ModelAttribute BlogIntroPost sum,
                            Model model) {
        commonProcess("intro", model);

        String sumCode = memberUtil.getMember().getUserId() + "_sum" ;
        sum = infoService.get(sumCode, BlogIntroPost.class).orElseGet(BlogIntroPost::new);

        model.addAttribute("introSum", sum) ;

        String code = memberUtil.getMember().getUserId() ;
        intro = infoService.get(code, BlogIntroPost.class).orElseGet(BlogIntroPost::new);

        model.addAttribute("blogIntroPost", intro);

        return "admin/farmer/blog/introduction";
    }

    /**
     * 소개글 저장
     */
    @PostMapping
    public String introSave(BlogIntroPost intro, Model model) {

        commonProcess("intro", model);

        String code = memberUtil.getMember().getUserId() ;
        saveService.save(code, intro);
        fileUploadService.processDone(intro.getGid());

        String script = String.format("alert('%s'); location.href='/farmer/myFarmBlog';", Utils.getMessage("저장되었습니다.", "commons"));
        model.addAttribute("script", script);

        return "common/_execute_script";
    }

    @PostMapping("/sum")
    public String introSumSave(BlogIntroPost sum, Model model) {
        commonProcess("intro", model);

        String code = memberUtil.getMember().getUserId() + "_sum" ;
        saveService.save(code, sum);

        String script = String.format("alert('%s'); location.href='/farmer/myFarmBlog';", Utils.getMessage("저장되었습니다.", "commons"));
        model.addAttribute("script", script);

        return "common/_execute_script";
    }

    /**
     * 리뷰관리 페이지
     */
    @GetMapping("/review")
    public String reviews(@ModelAttribute BoardDataSearch search, Model model) {
        commonProcess("review", model);

        String bid = "review" ;

        ListData<BoardData> data = boardInfoService.getList(bid, search);

        /* 게시판 설정 처리 */
        board = configInfoService.get(bid);
        model.addAttribute("board", board);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "/admin/farmer/blog/review" ;
    }

    /**
     * 소식 관리 페이지
     */
    @GetMapping("/sns")
    public String sns(@ModelAttribute BoardDataSearch search, Model model) {

        String bid = "sns_" + memberUtil.getMember().getUserId() ;
        search.setUserId(memberUtil.getMember().getUserId());
        ListData<BoardData> data = boardInfoService.getList(bid, search);

        /* 게시판 설정 처리 */
        board = configInfoService.get(bid); // 매번 DB조회
        model.addAttribute("board", board);
        commonProcess("sns", model);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "/admin/farmer/blog/sns" ;
    }

    /**
     * 공통 기능
     */
    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "intro");
        String pageTitle = "소개글관리";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        if (mode.equals("intro")) {
            addScript.add("farmer/blog_intro") ;
        } else if (mode.equals("review")) {
            pageTitle = "리뷰관리" ;

        } else if (mode.equals("sns")) {
            pageTitle = "소식관리" ;
            addScript.add("board/form");

            String skin = board.getSkin();
            addCss.add("board/skin_" + skin);
            addScript.add("board/skin_" + skin);

            pageTitle = board.getBName(); // 게시판명이 기본 타이틀
        }

        addCommonScript.add("ckeditor5/ckeditor");
        addCommonScript.add("fileManager");

        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript) ;
        model.addAttribute("addScript", addScript) ;
    }
}
