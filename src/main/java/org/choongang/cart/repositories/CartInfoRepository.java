package org.choongang.cart.repositories;

import com.querydsl.core.BooleanBuilder;
import org.choongang.cart.entities.CartInfo;
import org.choongang.cart.entities.QCartInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CartInfoRepository extends JpaRepository<CartInfo, Long>, QuerydslPredicateExecutor<CartInfo> {

}