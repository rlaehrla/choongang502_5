package org.choongang.product.repositories;

import org.choongang.member.entities.Farmer;
import org.choongang.product.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String>, QuerydslPredicateExecutor<Category> {
    Optional<List<Category>> findByFarmer(Farmer farmer);

}