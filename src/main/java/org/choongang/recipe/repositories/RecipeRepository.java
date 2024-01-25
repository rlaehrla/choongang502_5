package org.choongang.recipe.repositories;

import org.choongang.recipe.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface RecipeRepository extends JpaRepository<Recipe, Long>, QuerydslPredicateExecutor<Recipe> {

    @Query("SELECT DISTINCT r.keyword FROM Recipe r")
    List<String> getIngredients();

}
