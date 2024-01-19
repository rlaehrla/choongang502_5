package org.choongang.board.service.comment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.comment.RequestComment;
import org.choongang.board.entities.BoardData;
import org.choongang.board.entities.CommentData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.board.repositories.CommentDataRepository;
import org.choongang.member.MemberUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CommentSaveService {

    private final CommentDataRepository commentDataRepository; // 댓글 데이터
    private final BoardDataRepository boardDataRepository; // 게시판 데이터

    private final CommentInfoService commentInfoService;

    private final MemberUtil memberUtil; // 회원 정보
    private final PasswordEncoder encoder; // 비회원 정보
    private final HttpServletRequest request;
    // 저장, 수정 한꺼번에

    public CommentData save(RequestComment form) {

        String mode = form.getMode();
        Long seq = form.getSeq(); // 댓글 번호 (수정할때)


        mode = StringUtils.hasText(mode) ? mode : "add";

        CommentData data = null;
        // 조회하고 있으면 쓰고 없으면 던진다.
        if (mode.equals("edit") && seq != null) { // 댓글 수정
            data = commentDataRepository.findById(seq).orElseThrow(CommentNotFoundException::new);
        } else { // 댓글 추가
            data = new CommentData();
            // 게시글 번호는 변경 X -> 추가할 때 최초 1번만 반영
            Long boardDataSeq = form.getBoardDataSeq(); // 게시글 번호는 추가할 때만 넣고 수정x
            BoardData boardData = boardDataRepository.findById(boardDataSeq).orElseThrow(BoardDataNotFoundException::new);

            data.setBoardData(boardData);

            data.setMember(memberUtil.getMember());  // 추가할때 최초 1번만 반영

            data.setIp(request.getRemoteAddr()); // Ip주소 최초 1번
            data.setUa(request.getHeader("User-Agent")); // 브라우저 정보 최초 1번
        }

        // 비회원 비밀번호 있으면 -> 해시화 -> 저장
        String guestPw = form.getGuestPw();
        if (StringUtils.hasText(guestPw)) {
            data.setGuestPw(encoder.encode(guestPw));
        }

        String commenter = form.getCommenter();
        if (StringUtils.hasText(commenter)) {
            data.setCommenter(commenter);
        }

        data.setContent(form.getContent());

        commentDataRepository.saveAndFlush(data);

        commentInfoService.updateCommentCount(data.getBoardData().getSeq());

        return data;
    }
}
