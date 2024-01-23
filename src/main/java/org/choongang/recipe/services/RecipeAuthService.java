package org.choongang.recipe.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.AuthCheck;
import org.choongang.board.service.GuestPasswordCheckException;
import org.choongang.board.service.comment.CommentInfoService;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.member.constants.Authority;
import org.choongang.member.entities.AbstractMember;
import org.springframework.stereotype.Service;


/**
 * 레시피 권한 체크
 * 레시피 조회 : 모두 가능
 * 레시피 등록, 댓글 : 멤버, admin 가능
 *      수정, 삭제 : 작성자, admin 가능
 *
 *
 */
@Service
@RequiredArgsConstructor
public class RecipeAuthService{
    private final MemberUtil memberUtil;
    private final RecipeInfoService recipeInfoService;
    private final CommentInfoService commentInfoService;

    private final HttpSession session;


    /**
     * 레시피(댓글) 수정, 삭제
     *      user-작성자, admin O
     *      비회원, 농부 X
     */

    public void check(String mode, Long seq) {
        if(memberUtil.isAdmin()) { // 관리자는 체크 불필요
            return;
        }
        AuthCheck data = null;
        if (mode.indexOf("comment_") != -1) { // 댓글
            data = commentInfoService.get(seq);
        } else { // 레시피
            data = recipeInfoService.get(seq);
        }
        AbstractMember member = data.getMember();
        // 작성자가 아닐 때
        if ((mode.contains("update") && !data.isEditable())
                || (mode.contains("delete") && !data.isDeletable())) {
            throw new UnAuthorizedException();
        }

        // 비회원, 농부 -> 로그인 화면
        if(member == null || memberUtil.isFarmer()) {
            // 권한 없음
            throw new UnAuthorizedException(); // 임시
        }


        // alert -> back
        return;
    }
    /**
     * 레시피 등록
     * 유저, admin만
     *
     */
    public void accessCheck(String mode) {
        AuthCheck data = null;
        if (memberUtil.isAdmin()) { // 관리자는 체크 불필요
            return;
        }
        AbstractMember member = data.getMember();

        // 비회원이거나 농부 -> 권한 없음
        if(member == null || memberUtil.isFarmer()) {
            throw new UnAuthorizedException(); // 임시
        }
        // 작성자가 아닐 때
        if ((mode.contains("update") && !data.isEditable())
                || (mode.contains("delete") && !data.isDeletable())) {
            throw new UnAuthorizedException();
        }

    }

}
