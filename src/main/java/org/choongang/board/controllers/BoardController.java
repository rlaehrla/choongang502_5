package org.choongang.board.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.board.controllers.comment.RequestComment;
import org.choongang.board.entities.BoardData;
import org.choongang.board.service.BoardAuthService;
import org.choongang.board.service.BoardDeleteService;
import org.choongang.board.service.BoardInfoService;
import org.choongang.board.service.BoardSaveService;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.board.service.review.ReviewAuthService;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.farmer.blog.service.SellingInfoService;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.service.MemberInfoService;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.service.OrderItemInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController extends AbstractBoardController {


    public BoardController(ConfigInfoService confInfoService, BoardConfigInfoService configInfoService, FileInfoService fileInfoService, BoardFormValidator boardFormValidator, BoardSaveService boardSaveService, BoardInfoService boardInfoService, BoardDeleteService boardDeleteService, BoardAuthService boardAuthService, ReviewAuthService reviewAuthService, OrderItemInfoService orderItemInfoService, MemberUtil memberUtil, MemberInfoService memberInfoService, Utils utils, SellingInfoService sellingInfoService, HttpServletRequest request) {
        super(confInfoService, configInfoService, fileInfoService, boardFormValidator, boardSaveService, boardInfoService, boardDeleteService, boardAuthService, reviewAuthService, orderItemInfoService, memberUtil, memberInfoService, utils, sellingInfoService, request);
    }

    /**
     * 게시판 목록
     * @param bid : 게시판 아이디
     * @param model
     * @return
     */
    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid,
                       @ModelAttribute BoardDataSearch search, Model model) {

        commonProcess(bid, "list", model);

        ListData<BoardData> data = boardInfoService.getList(bid, search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("board/list");
    }

    /**
     * 게시글 보기
     * @param seq : 게시글 번호
     * @param model
     * @return
     */
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq,
                       @ModelAttribute BoardDataSearch search, Model model) {
        boardInfoService.updateViewCount(seq); // 조회수 업데이트

        commonProcess(seq, "view", model);

        // 게시글 보기 하단 목록 노출 S
        if (board.isShowListBelowView()) {
            ListData<BoardData> data = boardInfoService.getList(board.getBid(), search);

            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());
        }
        // 게시글 보기 하단 목록 노출 E

        // 댓글 커맨드 객체 처리
        RequestComment requestComment = new RequestComment();
        if (memberUtil.isLogin()) {
            requestComment.setCommenter(memberUtil.getMember().getNickname());
        }

        model.addAttribute("requestComment", requestComment);
        // 댓글 커맨드 객체 처리 E

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
    public String write(@PathVariable("bid") String bid,
                        @ModelAttribute RequestBoard form,
                        @RequestParam(name="orderItemSeq", required = false) Long orderItemSeq,
                        Model model ) {
        commonProcess(bid, "write", model);

        if (memberUtil.isLogin()) {
            AbstractMember member = memberUtil.getMember();
            form.setPoster(member.getUsername());
        }

        // 리뷰 게시판인 경우 상품정보 가져오기
        if (bid != null && bid.equals("review")) {
            if (orderItemSeq == null) {
                throw new AlertBackException(Utils.getMessage("Required.orderItemSeq"), HttpStatus.BAD_REQUEST);
            }


            reviewAuthService.check(orderItemSeq);

            OrderItem orderItem = orderItemInfoService.getOneItem(orderItemSeq);

            model.addAttribute("orderItem", orderItem);
        }

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

        RequestBoard form = boardInfoService.getForm(boardData);
        model.addAttribute("requestBoard", form);

        return utils.tpl("board/update");
    }


    @GetMapping("/reply/{seq}")
    public String reply(@PathVariable("seq") Long parentSeq,
                        @ModelAttribute RequestBoard form, Model model) {
        commonProcess(parentSeq, "reply", model);
        if (!board.isUseReply()) { // 답글 사용 불가
            throw new UnAuthorizedException();
        }

        String content = boardData.getContent();
        content = String.format("<br><br><br><br><br>===================================================<br>%s", content);

        form.setBid(board.getBid());
        form.setContent(content);
        form.setParentSeq(parentSeq);

        if (memberUtil.isLogin()) {
            form.setPoster(memberUtil.getMember().getNickname()); // 확인 필요
        }


        return utils.tpl("board/write");
    }

    /**
     * 게시글 등록, 수정
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid RequestBoard form, Errors errors, Model model) {
        String bid = form.getBid();
        String mode = form.getMode();
        commonProcess(bid, mode, model);

        boardFormValidator.validate(form, errors);

        if (errors.hasErrors()) {
            String gid = form.getGid();

            List<FileInfo> editorFiles = fileInfoService.getList(gid, "editor");
            List<FileInfo> attachFiles = fileInfoService.getList(gid, "attach");

            form.setEditorFiles(editorFiles);
            form.setAttachFiles(attachFiles);

            return utils.tpl("board/" + mode);
        }

        // 게시글 저장 처리
        BoardData boardData = boardSaveService.save(form);

        String redirectURL = "redirect:/board/";
        redirectURL += board.getLocationAfterWriting().equals("view") ? "view/" + boardData.getSeq() : "list/" + form.getBid(); // 글 작성후 이동 장소  - 게시글 상세 : 게시글 목록

        return redirectURL;
    }

    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "delete", model);

        boardDeleteService.delete(seq);

        return "redirect:/board/list/" + board.getBid();
    }

    /**
     * 비회원 글수정, 글삭제 비밀번호 확인
     *
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/password")
    public String passwordCheck(
            @RequestParam(name = "password", required = false) String password, Model model) {
                boardAuthService.validate(password);

                model.addAttribute("script", "parent.location.reload();");

                return "common/_execute_script";
    }
}
