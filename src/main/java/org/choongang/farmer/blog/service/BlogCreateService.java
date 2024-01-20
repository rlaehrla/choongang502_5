package org.choongang.farmer.blog.service;


import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardConfig;
import org.choongang.board.entities.Board;
import org.choongang.board.repositories.BoardRepository;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.board.service.config.BoardConfigSaveService;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogCreateService {
    private final BoardConfigSaveService boardConfigSaveService;
    private final BoardConfigInfoService boardConfigInfoService;
    private final BoardRepository boardRepository;

    private final MemberUtil memberUtil;

    /**
     * 블로그 게시판 생성
     */
    public Board create(String userId, String farmTitle) {

        // 이미 블로그가 생성되어 있다면 생성 X 블로그 게시판 설정만 반환
        String blogId = "sns_" + userId;
        if (!boardRepository.existsById(blogId)) { // 블로그가 등록되어 있지 않으면 하나 생성

            RequestBoardConfig form = new RequestBoardConfig();
            form.setMode("add");
            form.setBid(blogId);
            form.setActive(true);
            form.setBName("🔔" + farmTitle + "의 소식");

            boardConfigSaveService.save(form);
        }

        Board board = boardConfigInfoService.get(blogId);

        return board;
    }

    /**
     * 현재 로그인한 회원 아이디로 블로그 생성
     *
     * @return

    public Board create() {
        if (!memberUtil.isLogin()) {
            return null;
        }
        Member member = memberUtil.getMember();
        String blogTitle = String.format("%s님의 블로그", member.getName());
        return create(member.getUserId(), blogTitle);
    }*/
}
