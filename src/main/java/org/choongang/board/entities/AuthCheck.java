package org.choongang.board.entities;

import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Member;

public interface AuthCheck {
    boolean isEditable();
    boolean isDeletable();
    boolean isMine();
    AbstractMember getMember();
}
