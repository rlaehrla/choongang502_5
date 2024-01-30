package org.choongang.board.service.review;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.QBoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.repositories.OrderItemRepository;
import org.choongang.order.service.OrderItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewAuthService {

    private final BoardDataRepository boardDataRepository ;
    private final OrderItemRepository orderItemRepository ;
    private final MemberUtil memberUtil ;

    public void check(Long orderItemSeq) {
        /**
         * 품주번호로 후기 작성이 이미 진행되었는지 체크
         */
        QBoardData boardData = QBoardData.boardData ;
        BooleanBuilder builder = new BooleanBuilder() ;
        builder.and(boardData.board.bid.eq("review"))
                .and(boardData.num3.eq(orderItemSeq));

        if (boardDataRepository.exists(builder)) {
            throw new AlertBackException(Utils.getMessage("이미_작성된_후기입니다.", "errors"), HttpStatus.BAD_REQUEST);
        }

        /**
         * 회원 주문인 경우 로그인한 회원과 동일한지 체크
         */
        OrderItem orderItem = orderItemRepository.findById(orderItemSeq).orElseThrow(OrderItemNotFoundException::new);
        OrderInfo orderInfo = orderItem.getOrderInfo() ;
        AbstractMember orderMember = orderInfo.getMember() ;

        if (orderMember != null
                && (!memberUtil.isLogin() || !memberUtil.getMember().getUserId().equals(orderMember.getUserId()))) {
            throw new AlertBackException(Utils.getMessage("주문한_상품만_후기를_작성할_수_있습니다.", "errors"), HttpStatus.UNAUTHORIZED);
        }
    }
}
