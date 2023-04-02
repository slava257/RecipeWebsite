package me.safronov.recipewebsite.controllers;







import me.safronov.recipewebsite.services.FilesIngredientsServicesImpl;
import me.safronov.recipewebsite.services.impl.FilesRecipeServicesImpl;


import me.safronov.recipewebsite.services.impl.IngredientsImplServices;
import me.safronov.recipewebsite.services.impl.RecipeImplServices;

import org.apache.commons.io.IOUtils;

import org.springframework.core.io.InputStreamResource;



import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController
@RequestMapping("/files")
public class ImportExportController {
    private final FilesRecipeServicesImpl filesRecipeServices;
    private final FilesIngredientsServicesImpl fileIngredientsServices;
    private final IngredientsImplServices ingredientsImplServices;
    private final RecipeImplServices recipeImplServices;

    public ImportExportController(FilesRecipeServicesImpl filesRecipeServices, FilesIngredientsServicesImpl fileIngredientsServices, IngredientsImplServices ingredientsImplServices, RecipeImplServices recipeImplServices) {
        this.filesRecipeServices = filesRecipeServices;
        this.fileIngredientsServices = fileIngredientsServices;


        this.ingredientsImplServices = ingredientsImplServices;
        this.recipeImplServices = recipeImplServices;
    }


    //Первый эндпоинт позволяет скачать все рецепты в виде json-файла.
    // Второй эндпоинт принимает json-файл с рецептами и заменяет сохраненный на жестком (локальном) диске файл на новый.
    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile file) {
        filesRecipeServices.cleanDataFile();
        File uploadFile = filesRecipeServices.getRecipeFile();

        try (FileOutputStream fos = new FileOutputStream(uploadFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // Третий эндпоинт принимает json-файл с ингредиентами и заменяет сохраненный на жестком (локальном)
    //диске файл на новый.

    @PostMapping(value = "/import/ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) {
        fileIngredientsServices.cleanDataFile();
        ingredientsImplServices.readFormFile();
        File uploadIngredientsFile = fileIngredientsServices.getIngredientsFile();


        try (FileOutputStream fos = new FileOutputStream(uploadIngredientsFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(value = "/export")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File file =filesRecipeServices.getRecipeFile();
        if (file.exists()) {
            return recipeImplServices.downloadRecipeFile1();
        }
        return ResponseEntity.noContent().build();
    }
    //Задание
    //В приложении по работе с кулинарными рецептами создайте один эндпоинт, который позволяет скачать все рецепты из приложения в одном файле в виде:
    //
    //Пример скачанного рецепта
    //Обработайте исключительные ситуации. При возникновении исключений допишите приложение так, что клиенту будет отправляться HTTP-ответ с соответствующим кодом.
    //
    //Подсказка
    //При обработке ошибок обратите внимание в первую очередь на:
    //
    // Отсутствие данных по id;
    // Ошибки при работе с потоками данных и файлами;
    // Отсутствие рецептов или ингредиентов.
    //В результате у вас должно выйти полностью функционирующее приложение,
    // в которое можно загружать рецепты, удалять их, менять, получать данные через HTTP-запросы.
    // В вашем приложении обработаны ошибки и исключения,
    // при неверном вводе или выводе данных клиенту отправляется HTTP-ответ с соответствующим кодом.
    @GetMapping(value = "/export/download")
    public ResponseEntity<Object> downloadFileRecipe() {
        try {
            Path path = recipeImplServices.creatAddFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            return recipeImplServices.downloadFileTxtRecipe();

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());

        }
    }
}
