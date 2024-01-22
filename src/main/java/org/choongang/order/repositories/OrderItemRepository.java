package org.choongang.order.repositories;

import org.choongang.order.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, QuerydslPredicateExecutor<OrderItem> {
}
