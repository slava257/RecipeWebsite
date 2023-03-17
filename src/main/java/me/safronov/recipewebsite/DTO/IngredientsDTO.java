package me.safronov.recipewebsite.DTO;


import lombok.Data;
import me.safronov.recipewebsite.model.Ingredients;
@Data

public class IngredientsDTO {
    private int count;
    private String name;
    private int quantity;
    private String measuringUnit;

    public IngredientsDTO(int count, String name, int quantity, String measuringUnit) {
        this.count = count;
        this.name = name;
        this.quantity = quantity;
        this.measuringUnit = measuringUnit;
    }

    public static IngredientsDTO form(int count, Ingredients ingredients) {
        return new IngredientsDTO(count,ingredients.getName(),ingredients.getQuantity(),ingredients.getMeasuringUnit());
    }
}
