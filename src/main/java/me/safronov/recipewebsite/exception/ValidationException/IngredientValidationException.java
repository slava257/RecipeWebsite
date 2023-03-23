package me.safronov.recipewebsite.exception.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IngredientValidationException extends RuntimeException {
}
