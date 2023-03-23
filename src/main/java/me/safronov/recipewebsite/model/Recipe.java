package me.safronov.recipewebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
////Продолжим разрабатывать приложение для рецептов. Вам необходимо создать сервис, который будет хранить рецепты и возвращать рецепты по его идентификатору.
////
////Храниться рецепты должны в карте в формате <номер рецепта, рецепт>.
////
////Поля класса рецепта должны содержать:
////
////Название в формате строки;
////Время приготовления в минутах в формате целого положительного числа;
////Ингредиенты в формате списка объектов;
////Шаги приготовления в формате списка строк.
@Data
@AllArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredients> ingredients;
    private List<String> cookingInstructions;
}
