package me.safronov.recipewebsite.services.impl;

import me.safronov.recipewebsite.DTO.RecipeDTO;
import me.safronov.recipewebsite.model.Recipe;
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
public class RecipeImplServices  {
    private  int count = 0;
    private final Map<Integer, Recipe> recipes = new HashMap<>();


    public RecipeDTO addRecipe(Recipe recipe) {
        recipes.put(count++, recipe);
        return RecipeDTO.from(count,recipe);
    }


    public RecipeDTO getRecipes(int count) {
        Recipe recipe = recipes.get(count);
        if (recipe != null) {
            return RecipeDTO.from(count,recipe);
        }
        return null;
    }
}