package org.choongang.order.repositories;

import com.querydsl.core.BooleanBuilder;
import org.choongang.member.entities.Farmer;
import org.choongang.member.entities.QFarmer;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, QuerydslPredicateExecutor<OrderItem> {

    /*@Query("SELECT o.farmer FROM OrderItem o GROUP BY o.farmer ORDER BY( SELECT SUM(o2.ea) from OrderItem o2 WHERE o2.farmer = o.farmer) DESC")
    List<Farmer> getBestFarmers(@Param("date") LocalDateTime date);*/

    Optional<List<OrderItem>> findByOrderInfo(OrderInfo orderInfo);

}
