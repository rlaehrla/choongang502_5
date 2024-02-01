package org.choongang.order.repositories;

import com.querydsl.core.BooleanBuilder;
import org.choongang.member.entities.Farmer;
import org.choongang.member.entities.QFarmer;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.entities.QOrderItem;
import org.choongang.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, QuerydslPredicateExecutor<OrderItem> {

    default List<OrderItem> findByOrderInfoSeq(Long orderInfoSeq){
        QOrderItem orderItem = QOrderItem.orderItem;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orderItem.orderInfo.seq.eq(orderInfoSeq));

        Iterator<OrderItem> iterator = findAll(builder).iterator();
        List<OrderItem> orderItems = new ArrayList<>();
        while(iterator.hasNext()){
            orderItems.add(iterator.next());
        }
        return orderItems;
    }

}
