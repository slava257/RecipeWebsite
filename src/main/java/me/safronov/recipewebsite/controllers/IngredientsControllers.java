package me.safronov.recipewebsite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.safronov.recipewebsite.DTO.IngredientsDTO;
import me.safronov.recipewebsite.exception.IngredientsNotFoundException;
import me.safronov.recipewebsite.model.Ingredients;
import me.safronov.recipewebsite.services.impl.IngredientsImplServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Lombok (удалить всё, кроме полей из POJO-классов, и поставить нужные аннотации).
//Apache Commons (используйте утилитные методы из Apache Commons там, где это необходимо).
//Swagger (настройте генерацию UI с помощью Swagger, добавьте описание к контроллерам и эндпоинтам).
@RestController
@RequestMapping("/ingredients")
@Tag(name="Ингредиент", description = "Операции и другии эндопоинты для работы с ингредиентами" )
public class IngredientsControllers {
    private final IngredientsImplServices ingredientsImplServices;

    public IngredientsControllers(IngredientsImplServices ingredientsImplServices) {
        this.ingredientsImplServices = ingredientsImplServices;
    }

    @PostMapping()
    @Operation(summary = "Добавить ингредиент",
            description = "Можно добавить ингредиент только с названием")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент добавлен",
                    content ={
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public IngredientsDTO addIngredients(@RequestBody Ingredients ingredients) {
        return ingredientsImplServices.addIngredients(ingredients);
    }

    @GetMapping("/{count}")
    @Operation(summary = "Найти ингредиент",
            description = "Можно найти ингредиент по номеру")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден",
                    content ={
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public IngredientsDTO getIngredients(@PathVariable("count") int count) {
        return ingredientsImplServices.getIngredients(count);
    }

    //Продолжаем работать с приложением по рецептам.
    // К уже созданной структуре добавьте операции редактирования и удаления рецептов и ингредиентов .
    @PutMapping("/{count}")
    @Operation(summary = "Найти ингредиент и изменить",
            description = "Можно найти ингредиент по номеру и изменить его")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент изменен",
                    content ={
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public IngredientsDTO editIngredients(@PathVariable int count, @RequestBody Ingredients ingredients) {
        IngredientsDTO ingredientsDTO = ingredientsImplServices.editIngredients(count, ingredients);
        if (ingredientsDTO == null) {
            throw new IngredientsNotFoundException();
        }
        return ingredientsImplServices.editIngredients(count, ingredients);
    }
    @DeleteMapping("/{count}")
    @Operation(summary = "Удалить ингредиент",
            description = "Можно удалить ингредиент по номеру")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был удален",
                    content ={
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public IngredientsDTO deleteIngredients(@PathVariable int count) {
        IngredientsDTO ingredientsDTO = ingredientsImplServices.deleteIngredients(count);
        if (ingredientsDTO == null) {
            throw new IngredientsNotFoundException();
        }
        return ingredientsDTO;
    }
    @GetMapping()
    @Operation(summary = "Вывести ингредиент")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все ингредиенты",
                    content ={
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public List<IngredientsDTO> allGetIngredients() {
        return ingredientsImplServices.allGetIngredients();
    }
}
