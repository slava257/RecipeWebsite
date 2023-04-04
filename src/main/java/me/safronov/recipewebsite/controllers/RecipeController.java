package me.safronov.recipewebsite.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.safronov.recipewebsite.DTO.RecipeDTO;
import me.safronov.recipewebsite.exception.RecipeNotFoundException;
import me.safronov.recipewebsite.model.Recipe;

import me.safronov.recipewebsite.services.impl.RecipeImplServices;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//Lombok (удалить всё, кроме полей из POJO-классов, и поставить нужные аннотации).
//Apache Commons (используйте утилитные методы из Apache Commons там, где это необходимо).
//Swagger (настройте генерацию UI с помощью Swagger, добавьте описание к контроллерам и эндпоинтам).
@RestController
@RequestMapping("/recipe")
//Создайте контроллеры и API для создания и получения рецептов и ингредиентов .
@Tag(name = "Рецепты", description = "Операции и другии эндопоинты для работы с книгой рецептов")
public class RecipeController {
    private final RecipeImplServices recipeImplServices;


    public RecipeController(RecipeImplServices recipeImplServices) {
        this.recipeImplServices = recipeImplServices;

    }

    @PostMapping()
    @Operation(summary = "Добавить рецепт",
            description = "Можно добавить рецепт только с названием")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public RecipeDTO addRecipe(@RequestBody Recipe recipe) {

        return recipeImplServices.addRecipe(recipe);
    }

    @GetMapping("/{count}")
    @Operation(summary = "Поиск рецепта по номеру",
            description = "Можно искать по номеру count")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })

    public RecipeDTO getRecipes(@PathVariable("count") int count) {

        return recipeImplServices.getRecipes(count);
    }

    //Продолжаем работать с приложением по рецептам.
    // К уже созданной структуре добавьте операции редактирования и удаления рецептов и ингредиентов.
    @PutMapping("/{count}")
    @Operation(summary = "Изменить рецепт",
            description = "Можно найти рецепт по номеру и изменить его")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был изменить",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public RecipeDTO editRecipe(@PathVariable int count, @RequestBody Recipe recipe) {
        RecipeDTO recipeDTO = recipeImplServices.editRecipe(count, recipe);
        if (recipeDTO != null) {
            return recipeImplServices.editRecipe(count, recipe);
        }
        throw new RecipeNotFoundException();
    }

    @DeleteMapping("/{count}")
    @Operation(summary = "Удалить рецепт",
            description = "Можно удалить рецепт по номеру")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был удален",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public RecipeDTO deleteRecipe(@PathVariable int count) {
        RecipeDTO recipeDTO = recipeImplServices.deleteRecipe(count);
        if (recipeDTO != null) {
            return recipeDTO;
        }
        throw new RecipeNotFoundException();
    }

    @GetMapping()
    @Operation(summary = "Вывести все рецепты")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все рецепты",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public List<RecipeDTO> allGetRecipe() {
        return recipeImplServices.allGetRecipes();
    }


    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addRecipeFromFile(@RequestParam MultipartFile file) {
        try (InputStream stream = file.getInputStream()) {
            recipeImplServices.addRecipeFromInputStream(stream);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
}
