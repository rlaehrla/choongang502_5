package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.Board;
import org.choongang.board.entities.BoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController implements ExceptionProcessor {

    private final BoardConfigInfoService configInfoService; // 게시판 설정 조회 서비스

    private final Utils utils;

    private Board board; // 게시판 설정

    @GetMapping("/") // 메인 페이지 이동
    public String main(){
        return utils.tpl("board/index");
    }

    @GetMapping("/policy/terms_of_service") // 서비스 이용약관 이동
    public String service() {
        return utils.tpl("outlines/terms_of_service");
    }

    @GetMapping("/policy/privacy") // 개인정보 처리방침 이동
    public String privacy() {
        return utils.tpl("outlines/privacy");
    }

    /**
     * 게시판 목록
     * @param bid : 게시판 아이디
     * @param model
     * @return
     */
    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid, Model model) {
        commonPrecess(bid, "list", model);

        return utils.tpl("board/list");
    }

    /**
     * 게시글 보기
     * @param seq : 게시글 번호
     * @param model
     * @return
     */
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "view", model);

        return utils.tpl("board/view");
    }

    /**
     * 게시글 작성
     *
     * @param bid
     * @param model
     * @return
     */
    @GetMapping("/write/{bid}")
    public String write(@PathVariable("bid") String bid, Model model) {
        commonPrecess(bid, "write", model);

        return utils.tpl("board/write");
    }

    /**
     * 게시글 수정
     *
     * @param seq
     * @param model
     * @return
     */
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "update", model);

        return utils.tpl("board/update");
    }

    /**
     * 게시글 등록, 수정
     * @param model
     * @return
     */
    @PatchMapping("/save")
    public String save(Model model) {

        return null;
    }

    /**
     * 게시판의 공통 처리 - 글목록, 글쓰기 등 게시판 ID가 있는 경우
     *
     * @param bid : 게시판 아이디
     * @param mode
     * @param model
     */
    private void commonPrecess(String bid, String mode, Model model) {

        mode = StringUtils.hasText(mode) ? mode : "list";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        List<String> addCommonCss = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        /* 게시판 설정 처리 S */

        board = configInfoService.get(bid); // 매번 DB조회

        // 스킨별 css, js 추가
        String skin = board.getSkin();
        addCss.add("board/skin_" + skin);
        addScript.add("board/skin_" + skin);

        model.addAttribute("board", board);

        /* 게시판 설정 처리 E */

        String pageTitle = board.getBName(); // 게시판명이 기본 타이틀

        if (mode.equals("write") || mode.equals("update")) { // 쓰기 또는 수정
            if (board.isUseEditor()) { // 에디터 사용하는 경우
                addCommonScript.add("ckeditor5/ckeditor");
            }

            // 이미지 또는 파일 첨부를 사용하는 경우
            if (board.isUseUploadImage() || board.isUseUploadFile()) {
                addCommonScript.add("fileManager");
            }

            addScript.add("board/form");

            pageTitle += " ";
            pageTitle += mode.equals("update") ?  Utils.getMessage("글수정", "commons") :  Utils.getMessage("글쓰기", "commons");
        }

        model.addAttribute("addCommonCss", addCommonCss);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("pageTitle", pageTitle);
    }

    /**
     * 게시판 공통 처리 : 게시글 보기, 게시글 수정 - 게시글 번호가 있는 경우
     *              - 게시글 조회 -> 게시판 설정
     * @param seq
     * @param mode
     * @param model
     */
    private void commonProcess(Long seq, String mode, Model model) {

    }

}
