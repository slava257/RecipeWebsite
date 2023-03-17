package me.safronov.recipewebsite.controllers;

import me.safronov.recipewebsite.DTO.IngredientsDTO;
import me.safronov.recipewebsite.model.Ingredients;
import me.safronov.recipewebsite.services.impl.IngredientsImplServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
public class IngredientsControllers {
    private final IngredientsImplServices ingredientsImplServices;

    public IngredientsControllers(IngredientsImplServices ingredientsImplServices) {
        this.ingredientsImplServices = ingredientsImplServices;
    }
    @PostMapping()
    public IngredientsDTO addIngredients(@RequestBody Ingredients ingredients) {
        return ingredientsImplServices.addIngredients(ingredients);
    }
    @GetMapping("/{count}")
    public IngredientsDTO getIngredients(@PathVariable("count") int count) {
        return ingredientsImplServices.getIngredients(count);
    }
}
