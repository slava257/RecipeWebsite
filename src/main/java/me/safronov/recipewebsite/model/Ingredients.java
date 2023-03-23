package me.safronov.recipewebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;

//Поля класса ингредиента должны содержать:
//
//Название в формате строки;
//Количество ингредиентов в формате целого положительного числа;
//Единица измерения в формате строки.
@Data
@AllArgsConstructor
public class Ingredients {
    private String name;
    private int quantity;
    private String measuringUnit;

}
