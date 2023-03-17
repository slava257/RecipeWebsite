package me.safronov.recipewebsite.services.impl;
import me.safronov.recipewebsite.DTO.IngredientsDTO;
import me.safronov.recipewebsite.exception.IngredientsNotFoundException;
import me.safronov.recipewebsite.model.Ingredients;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class IngredientsImplServices {
    private int idCount = 0;
    Map<Integer, Ingredients> idIngredientsMap = new HashMap<>();

    public IngredientsDTO addIngredients(Ingredients ingredients) {
        idIngredientsMap.put(idCount++, ingredients);
        return IngredientsDTO.form(idCount, ingredients);
    }

    public IngredientsDTO getIngredients(int count) {
        Ingredients ingredients = idIngredientsMap.get(count);
        if (ingredients != null) {
            return IngredientsDTO.form(count, ingredients);
        }
        return null;
    }

    public IngredientsDTO editIngredients(int count, Ingredients ingredients) {
        if (idIngredientsMap.containsKey(count)) {
            idIngredientsMap.put(count, ingredients);
            return IngredientsDTO.form(count, ingredients);
        }
        throw new IngredientsNotFoundException();
    }

    public IngredientsDTO deleteIngredients(int count) {
        Ingredients ingredients = idIngredientsMap.remove(count);
        if (ingredients != null) {
            return IngredientsDTO.form(count, ingredients);
        }
        throw new IngredientsNotFoundException();
    }


    public List<IngredientsDTO> allGetIngredients() {
        List<IngredientsDTO> result = new ArrayList<>();
        for (Map.Entry<Integer, Ingredients> entry : idIngredientsMap.entrySet()) {
            result.add(IngredientsDTO.form(entry.getKey(), entry.getValue()));

        }
        return result;
    }
}