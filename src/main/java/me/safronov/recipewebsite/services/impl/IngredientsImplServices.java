package me.safronov.recipewebsite.services.impl;

import me.safronov.recipewebsite.DTO.IngredientsDTO;
import me.safronov.recipewebsite.model.Ingredients;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientsImplServices  {
    private  int count = 0;
    Map<Integer, Ingredients> idIngredientsMap = new HashMap<>();

    public IngredientsDTO addIngredients(Ingredients ingredients) {
        idIngredientsMap.put(count++, ingredients);
        return IngredientsDTO.form(count, ingredients);
    }

    public IngredientsDTO getIngredients(int count) {
        Ingredients ingredients = idIngredientsMap.get(count);
        if (ingredients != null) {
            return IngredientsDTO.form(count,ingredients);
        }
        return null;
    }
}