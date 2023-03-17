package me.safronov.recipewebsite.controllers;



import me.safronov.recipewebsite.DTO.RecipeDTO;
import me.safronov.recipewebsite.model.Recipe;
import me.safronov.recipewebsite.services.impl.RecipeImplServices;
import org.springframework.web.bind.annotation.*;

@RestController
//Создайте контроллеры и API для создания и получения рецептов и ингредиентов.
public class RecipeController {
    private final RecipeImplServices recipeImplServices;

    public RecipeController(RecipeImplServices recipeImplServices) {
        this.recipeImplServices = recipeImplServices;
    }

    @PostMapping("/recipe")

    public RecipeDTO addRecipe(@RequestBody Recipe recipe) {

        return recipeImplServices.addRecipe(recipe);
    }

    @GetMapping("/recipe/{count}")

    public RecipeDTO getRecipes(@PathVariable("count") int count) {

        return recipeImplServices.getRecipes(count);
    }
}
