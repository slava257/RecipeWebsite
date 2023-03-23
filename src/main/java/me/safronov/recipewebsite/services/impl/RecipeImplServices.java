package me.safronov.recipewebsite.services.impl;


import me.safronov.recipewebsite.DTO.RecipeDTO;
import me.safronov.recipewebsite.exception.IngredientsNotFoundException;
import me.safronov.recipewebsite.exception.RecipeNotFoundException;
import me.safronov.recipewebsite.exception.ValidationException.RecipeValidationException;
import me.safronov.recipewebsite.model.Ingredients;
import me.safronov.recipewebsite.model.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import java.util.*;


//В сервисе должны быть реализованы методы:
//1. Добавления нового рецепта.
//В метод передается заполненный объект класса рецепта, который сохраняется в карте и получает свой порядковый
// номер.
//2. Получение рецепта.
//В метод передается порядковый номер рецепта, на выходе мы получаем из карты нужный объект.
//Создайте такой же сервис, но для работы с ингредиентами:
//Храниться ингредиенты должны в карте в формате <идентификатор, ингредиент>;
//В сервисе должны быть методы для добавления нового ингредиента и получения его по идентификатору.
// Можно делать по аналогии с сервисом рецептов.
@Service
public class RecipeImplServices {
    private int count = 0;
    private final IngredientsImplServices ingredientsImplServices;
    private final Map<Integer, Recipe> recipes = new HashMap<>();

    public RecipeImplServices(IngredientsImplServices ingredientsImplServices) {
        this.ingredientsImplServices = ingredientsImplServices;
    }


    public RecipeDTO addRecipe(Recipe recipe) {
        if (StringUtils.isBlank(recipe.getName())) {
            throw new RecipeValidationException();
        }
        recipes.put(count++, recipe );
        for (Ingredients ingredients : recipe.getIngredients()) {
            this.ingredientsImplServices.addIngredients(ingredients);
        }
        return RecipeDTO.from(count, recipe);
    }



    public RecipeDTO getRecipes(int count) {
        Recipe recipe = recipes.get(count);
        if (recipe != null) {
            return RecipeDTO.from(count, recipe);
        }
        throw new RecipeNotFoundException();
    }

    public RecipeDTO editRecipe(int count, Recipe recipe) {
        if (recipes.containsKey(count)) {
            if (StringUtils.isBlank(recipe.getName())) {
                throw new RecipeValidationException();
            }
            recipes.put(count, recipe);
            return RecipeDTO.from(count, recipe);
        }
        throw new RecipeNotFoundException();
    }

    public RecipeDTO deleteRecipe(int count) {
        Recipe recipe = recipes.get(count);
        if (recipes.containsKey(count)) {
            recipes.remove(count);
            return RecipeDTO.from(count, recipe);
        }
        throw new IngredientsNotFoundException();
    }
    public List<RecipeDTO> allGetRecipes() {
       List<RecipeDTO> result =new ArrayList<>();
       for(Map.Entry<Integer,Recipe> entry : recipes.entrySet()){
           result.add(RecipeDTO.from(entry.getKey(),entry.getValue()));

        }
        return result;
    }
}