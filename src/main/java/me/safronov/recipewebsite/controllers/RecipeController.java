package me.safronov.recipewebsite.controllers;
import me.safronov.recipewebsite.DTO.RecipeDTO;
import me.safronov.recipewebsite.exception.RecipeNotFoundException;
import me.safronov.recipewebsite.model.Recipe;
import me.safronov.recipewebsite.services.impl.RecipeImplServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
//Создайте контроллеры и API для создания и получения рецептов и ингредиентов.
public class RecipeController {
    private final RecipeImplServices recipeImplServices;

    public RecipeController(RecipeImplServices recipeImplServices) {
        this.recipeImplServices = recipeImplServices;
    }

    @PostMapping()

    public RecipeDTO addRecipe(@RequestBody Recipe recipe) {

        return recipeImplServices.addRecipe(recipe);
    }

    @GetMapping("/{count}")

    public RecipeDTO getRecipes(@PathVariable("count") int count) {

        return recipeImplServices.getRecipes(count);
    }

    //Продолжаем работать с приложением по рецептам.
    // К уже созданной структуре добавьте операции редактирования и удаления рецептов и ингредиентов.
    @PutMapping("/{count}")
    public RecipeDTO editRecipe(@PathVariable int count, @RequestBody Recipe recipe) {
        RecipeDTO recipeDTO = recipeImplServices.editRecipe(count,recipe);
        if (recipeDTO != null) {
            return recipeImplServices.editRecipe(count, recipe);
        }
        throw new RecipeNotFoundException();
    }

    @DeleteMapping("/{count}")
    public RecipeDTO deleteRecipe(@PathVariable int count) {
        RecipeDTO recipeDTO = recipeImplServices.deleteRecipe(count);
        if (recipeDTO != null) {
            return recipeImplServices.deleteRecipe(count);
        }
        throw new RecipeNotFoundException();
    }
    @GetMapping()
    public List<RecipeDTO> allGetRecipe() {
        return recipeImplServices.allGetRecipes();
    }

}
