package org.choongang.recipe.repositories;

import org.choongang.recipe.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 레시피 모두 보기 test
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long>, QuerydslPredicateExecutor<Recipe> {
/*    private static final Map<Long, Recipe> store = new HashMap<>();
    private static long sequence = 0L;

    public Recipe save(Recipe recipe) {
        recipe.setId(++sequence);
        store.put(recipe.getId(), recipe);
        return recipe;
    }

    public List<Recipe> findAll() {
        return new ArrayList<>(store.values());
    }

    public Recipe findById(long seq){
        return store.get(seq);
    }*/

}
