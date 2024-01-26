package org.choongang.order.repositories;

import com.querydsl.core.BooleanBuilder;
import org.choongang.order.constants.OrderStatus;
import org.choongang.order.entities.OrderStatusHistory;
import org.choongang.order.entities.QOrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long>, QuerydslPredicateExecutor<OrderStatusHistory> {

    /**
     * 이메일 전송 여부 확인
     * @param orderSeq
     * @param status
     * @return
     */
    default boolean isEmailSent(Long orderSeq, OrderStatus status){
        QOrderStatusHistory history = QOrderStatusHistory.orderStatusHistory;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(history.orderSeq.eq(orderSeq))
                .and(history.status.eq(status))
                .and(history.emailSent.eq(true));


        return exists(builder);




    }
}
