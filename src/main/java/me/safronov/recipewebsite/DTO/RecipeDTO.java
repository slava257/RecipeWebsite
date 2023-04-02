package me.safronov.recipewebsite.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import me.safronov.recipewebsite.model.Ingredients;
import me.safronov.recipewebsite.model.Recipe;


import java.util.List;

@Data
@AllArgsConstructor

public class RecipeDTO {
    private final int count ;
    private final String name;
    private final int cookingTime;
    private final List <Ingredients> ingredients;
    private final List<String> cookingInstructions;



    public static RecipeDTO from(int count, Recipe recipe) {
        return new RecipeDTO(count, recipe.getName(), recipe.getCookingTime(), recipe.getIngredients(),recipe.getCookingInstructions());
    }

}