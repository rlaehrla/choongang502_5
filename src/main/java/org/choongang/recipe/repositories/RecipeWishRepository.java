package org.choongang.recipe.repositories;

import org.choongang.recipe.entities.RecipeWish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RecipeWishRepository extends JpaRepository<RecipeWish, Long>, QuerydslPredicateExecutor<RecipeWish> {

}
