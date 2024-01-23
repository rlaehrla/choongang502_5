package org.choongang.board.service.comment;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends AlertBackException {
    // 댓글이 있는지 조회하고 없으면 예외를 던진다.

    public CommentNotFoundException() {

        super(Utils.getMessage("NotFound.comment", "errors"), HttpStatus.NOT_FOUND);
    }

}
