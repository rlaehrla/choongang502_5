package org.choongang.recipe.repositories;


import org.choongang.recipe.entities.RecipeCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RecipeCateRepository extends JpaRepository<RecipeCate, String>, QuerydslPredicateExecutor<RecipeCate> {
}
