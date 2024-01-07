package org.choongang.board.repositories.recipe;

import org.choongang.board.entities.recipe.Recipe;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 레시피 모두 보기 test
 */
@Repository
public class RecipeRepository {
    private static final Map<Long, Recipe> store = new HashMap<>();
    private static long sequence = 0L;

    public void save(Recipe recipe) {
        recipe.setId(++sequence);
        store.put(recipe.getId(), recipe);
    }

    public List<Recipe> findAll() {
        return new ArrayList<>(store.values());
    }
}
