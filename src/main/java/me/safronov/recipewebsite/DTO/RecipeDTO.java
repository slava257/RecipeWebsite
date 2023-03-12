package me.safronov.recipewebsite.DTO;


import lombok.Data;
import me.safronov.recipewebsite.model.Ingredients;
import me.safronov.recipewebsite.model.Recipe;

import java.util.List;

@Data

public class RecipeDTO {
    private final int count ;
    private final String name;
    private final int cookingTime;
    private final List <Ingredients> ingredients;
    private final List<String> cookingInstructions;

    public RecipeDTO(int count, String name, int cookingTime, List<Ingredients> ingredients, List<String> cookingInstructions) {
        this.count = count;
        this.name = name;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.cookingInstructions = cookingInstructions;
    }

    public static RecipeDTO from(int count, Recipe recipe) {
        return new RecipeDTO(count, recipe.getName(), recipe.getCookingTime(), recipe.getIngredients(),recipe.getCookingInstructions());
    }
}