package org.choongang.recipe.repositories;

import org.choongang.recipe.entities.Ingredient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 레시피 모두 보기 test
 */

@Repository
public class IngredientRepository {
    private static final Map<Long, Ingredient> ingStore = new HashMap<>();
    private static long sequence = 0L;

    public void save(Ingredient ingredient) {
        ingredient.setId(++sequence);
        ingStore.put(ingredient.getId(), ingredient);
    }

    public List<Ingredient> findAll() {
        return new ArrayList<>(ingStore.values());
    }
}
