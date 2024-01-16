package org.choongang.recipe.repositories;

import org.choongang.recipe.entities.HowToCook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HowToCookRepository extends JpaRepository<HowToCook, Long>, QuerydslPredicateExecutor<HowToCook> {
}
