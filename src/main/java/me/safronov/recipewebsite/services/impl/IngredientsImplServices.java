package me.safronov.recipewebsite.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.safronov.recipewebsite.DTO.IngredientsDTO;
import me.safronov.recipewebsite.exception.ValidationException.IngredientValidationException;
import me.safronov.recipewebsite.exception.IngredientsNotFoundException;
import me.safronov.recipewebsite.model.Ingredients;



import me.safronov.recipewebsite.services.FilesIngredientsServicesImpl;
import org.apache.commons.lang3.StringUtils;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;



import java.util.*;


@Service
public class IngredientsImplServices {

    private Integer idCount = 0;
    private HashMap<Integer, Ingredients> idIngredientsMap = new HashMap<>();
    private final FilesIngredientsServicesImpl filesIngredientsServices;


    public IngredientsImplServices(FilesIngredientsServicesImpl filesIngredientsServices ) {
        this.filesIngredientsServices = filesIngredientsServices;

    }


    @PostConstruct
    private void init() {
        try {
            readFormFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IngredientsDTO addIngredients(Ingredients ingredients) {
        if (StringUtils.isBlank(ingredients.getName())) {
            throw new IngredientValidationException();
        }
        idIngredientsMap.put(idCount++, ingredients);
        saveToFile();
        return IngredientsDTO.form(idCount, ingredients);
    }

    public IngredientsDTO getIngredients(int count) {
        Ingredients ingredients = idIngredientsMap.get(count);
        if (ingredients != null) {
            return IngredientsDTO.form(count, ingredients);
        }
        throw new IngredientsNotFoundException();
    }

    public IngredientsDTO editIngredients(int count, Ingredients ingredients) {
        if (idIngredientsMap.containsKey(count)) {
            if (StringUtils.isBlank(ingredients.getName())) {
                throw new IngredientValidationException();
            }
            idIngredientsMap.put(count, ingredients);
            saveToFile();
            return IngredientsDTO.form(count, ingredients);
        }
        throw new IngredientsNotFoundException();
    }

    public IngredientsDTO deleteIngredients(int count) {
        Ingredients ingredients = idIngredientsMap.remove(count);
        if (ingredients != null) {
            saveToFile();
            return IngredientsDTO.form(count, ingredients);
        }
        throw new IngredientsNotFoundException();
    }


    public List<IngredientsDTO> allGetIngredients() {
        List<IngredientsDTO> result = new ArrayList<>();
        for (Map.Entry<Integer, Ingredients> entry : idIngredientsMap.entrySet()) {
            result.add(IngredientsDTO.form(entry.getKey(), entry.getValue()));

        }
        return result;
    }

    private void saveToFile() {
        try {
            DataFile dataFile = new DataFile(idCount,idIngredientsMap) ;
            String json = new ObjectMapper().writeValueAsString(dataFile);
            filesIngredientsServices.saveToFile(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFormFile() {
        try {
            String json = filesIngredientsServices.readFromFile();
            if (!StringUtils.isEmpty(json)) {
                DataFile dataFile = new ObjectMapper().readValue(json, new TypeReference<>() {
                });
                idCount = dataFile.getIdCount();
                idIngredientsMap = dataFile.getIdIngredientsMap();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DataFile {
        private Integer idCount;
        private HashMap<Integer, Ingredients> idIngredientsMap;
    }
}

