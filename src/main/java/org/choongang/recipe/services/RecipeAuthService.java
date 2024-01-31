package org.choongang.recipe.services;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.AuthCheck;
import org.choongang.board.service.comment.CommentInfoService;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.recipe.controllers.RequestRecipe;
import org.springframework.http.HttpStatus;
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
        AbstractMember member = data.getMember(); // 작성한 멤버

        if(memberUtil.isLogin()) {
            // 작성한 멤버가 X, 농부일 때
            if (!(member.getNickname().equals(memberUtil.getMember().getNickname()))
                || memberUtil.isFarmer()) {
                throw new UnAuthorizedException();
            }
        } else if(memberUtil.getMember() == null) { // 비회원일 때
            throw new UnAuthorizedException(); // 임시
        }
    }
    /**
     * 레시피 등록
     * 유저, admin만 가능
     *
     */
    public void accessCheck(RequestRecipe form) {
        AbstractMember member = null;
        if (memberUtil.isAdmin()) { // 관리자는 체크 불필요
            member = memberUtil.getMember();
            form.setPoster("여름지기 마켓");
            return;
        }
        if (memberUtil.isLogin()) {
            member = memberUtil.getMember();
            form.setPoster(member.getNickname());
        }

        // 농부 -> 일반회원으로 로그인 안내
        if(memberUtil.isFarmer()){
            throw new AlertBackException(Utils.getMessage("NotFarmer", "errors"), HttpStatus.BAD_REQUEST);
        }

    }

}
