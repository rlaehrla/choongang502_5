package org.choongang.member.repositories;

import org.choongang.member.entities.Member;
import org.choongang.product.entities.ProductWish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductWishRepository extends JpaRepository<ProductWish, Long>, QuerydslPredicateExecutor<ProductWish> {

    Optional<List<ProductWish>> findByMember(Member member);
}
