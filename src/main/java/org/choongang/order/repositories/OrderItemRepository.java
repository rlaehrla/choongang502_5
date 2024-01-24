package org.choongang.order.repositories;

import org.choongang.order.entities.OrderItem;
import org.choongang.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, QuerydslPredicateExecutor<OrderItem> {

    @Query("SELECT p, SUM(o.ea) FROM OrderItem o LEFT JOIN o.product p WHERE o.createdAt >= :date GROUP BY p ORDER BY SUM(o.ea)")
    List<Object[]> getEaSum(@Param("date") LocalDateTime date);

}
