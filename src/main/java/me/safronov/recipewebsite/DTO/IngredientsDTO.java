package me.safronov.recipewebsite.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import me.safronov.recipewebsite.model.Ingredients;
@Data
@AllArgsConstructor

public class IngredientsDTO {
    private int count;
    private String name;
    private int quantity;
    private String measuringUnit;


    public static IngredientsDTO form(int count, Ingredients ingredients) {
        return new IngredientsDTO(count,ingredients.getName(),ingredients.getQuantity(),ingredients.getMeasuringUnit());
    }
}
