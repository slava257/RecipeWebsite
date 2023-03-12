package me.safronov.recipewebsite.controllers;


import me.safronov.recipewebsite.DTO.IngredientsDTO;
import me.safronov.recipewebsite.DTO.RecipeDTO;
import me.safronov.recipewebsite.model.Ingredients;
import me.safronov.recipewebsite.model.Recipe;
import me.safronov.recipewebsite.services.impl.IngredientsImplServices;
import me.safronov.recipewebsite.services.impl.RecipeImplServices;
import org.springframework.web.bind.annotation.*;

@RestController
//Создайте контроллеры и API для создания и получения рецептов и ингредиентов.
public class RecipeController  {
    private final RecipeImplServices recipeImplServices;
    private final IngredientsImplServices ingredientsImplServices;
    public RecipeController(RecipeImplServices recipeImplServices, IngredientsImplServices ingredientsImplServices) {
        this.recipeImplServices = recipeImplServices;
        this.ingredientsImplServices=ingredientsImplServices;
    }

    @PostMapping("/recipe")

    public RecipeDTO addRecipe(@RequestBody Recipe recipe) {

        return  recipeImplServices.addRecipe(recipe);
    }
    @GetMapping("/recipe/{count}")

    public RecipeDTO getRecipes(@PathVariable("count") int count) {

        return recipeImplServices.getRecipes(count);
    }

    @PostMapping("/ingredients")
    public IngredientsDTO addIngredients(@RequestBody Ingredients ingredients) {
        return ingredientsImplServices.addIngredients(ingredients);
    }
    @GetMapping("/ingredients/{count}")
    public IngredientsDTO getIngredients(@PathVariable("count") int count) {
        return ingredientsImplServices.getIngredients(count);
    }
}
