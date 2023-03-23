package me.safronov.recipewebsite.controllers;

import me.safronov.recipewebsite.DTO.IngredientsDTO;
import me.safronov.recipewebsite.exception.IngredientsNotFoundException;
import me.safronov.recipewebsite.model.Ingredients;
import me.safronov.recipewebsite.services.impl.IngredientsImplServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //Продолжаем работать с приложением по рецептам.
    // К уже созданной структуре добавьте операции редактирования и удаления рецептов и ингредиентов .
    @PutMapping("/{count}")
    public IngredientsDTO editIngredients(@PathVariable int count, @RequestBody Ingredients ingredients) {
        IngredientsDTO ingredientsDTO = ingredientsImplServices.editIngredients(count, ingredients);
        if (ingredientsDTO == null) {
            throw new IngredientsNotFoundException();
        }
        return ingredientsImplServices.editIngredients(count, ingredients);
    }
    @DeleteMapping("/{count}")
    public IngredientsDTO deleteIngredients(@PathVariable int count) {
        IngredientsDTO ingredientsDTO = ingredientsImplServices.deleteIngredients(count);
        if (ingredientsDTO == null) {
            throw new IngredientsNotFoundException();
        }
        return ingredientsDTO;
    }
    @GetMapping()
    public List<IngredientsDTO> allGetIngredients() {
        return ingredientsImplServices.allGetIngredients();
    }
}
