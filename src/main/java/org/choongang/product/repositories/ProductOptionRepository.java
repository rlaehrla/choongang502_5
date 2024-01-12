package org.choongang.product.repositories;

import org.choongang.product.entities.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long>, QuerydslPredicateExecutor<ProductOption> {

}
