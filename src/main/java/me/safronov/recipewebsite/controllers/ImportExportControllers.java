package me.safronov.recipewebsite.controllers;




import me.safronov.recipewebsite.services.FilesIngredientsServicesImpl;
import me.safronov.recipewebsite.services.impl.FilesRecipeServicesImpl;


import org.apache.commons.io.IOUtils;

import org.springframework.core.io.InputStreamResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;



@RestController
@RequestMapping("/files")
public class ImportExportControllers {
    private final FilesRecipeServicesImpl filesRecipeServices;
    private final FilesIngredientsServicesImpl fileIngredientsServices;


    public ImportExportControllers(FilesRecipeServicesImpl filesRecipeServices, FilesIngredientsServicesImpl fileIngredientsServices) {
        this.filesRecipeServices = filesRecipeServices;
        this.fileIngredientsServices = fileIngredientsServices;



    }


    //Первый эндпоинт позволяет скачать все рецепты в виде json-файла.
    @GetMapping(value = "/export")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File file = filesRecipeServices.getRecipeFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment ; filename=\"Recipe.json\"")
                    .body(resource);

        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // Второй эндпоинт принимает json-файл с рецептами и заменяет сохраненный на жестком (локальном) диске файл на новый.
    @PostMapping(value = "/import/recipe",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @PostMapping(value = "/import/ingredients",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) {
        fileIngredientsServices.cleanDataFile();
        File uploadIngredientsFile = fileIngredientsServices.getIngredientsFile();


        try (FileOutputStream fos = new FileOutputStream(uploadIngredientsFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
   }
