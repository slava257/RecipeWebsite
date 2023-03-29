package me.safronov.recipewebsite.services.impl;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
//Задание
//Продолжим работать с проектом по книге рецептов. Вам необходимо настроить сохранение загруженных рецептов и добавленных ингредиентов в файлы.
//Важно: сохранение загруженных рецептов и добавленных ингредиентов должно происходить в разные файлы.
//Требования к файлам:
//Формат json;
//Путь к файлу должен быть в application.properties;
//Описание этого шага можно найти в шпаргалке к уроку и в видео.
//Сохранение должно происходить при любом изменении рецептов и/или ингредиентов.
//При запуске приложения данные нужно читать из файла с помощью метода с аннотацией @PostConstruct в сервисе.
//Описание этого шага можно найти в видеоуроке и шпаргалке к уроку.
//Обработайте ошибки, которые могут возникнуть, — самостоятельно определите, какие это могут быть ошибки.
@Service

public class FilesRecipeServicesImpl {
    @Value("${path.to.recipe.file}")
    private String dataFilePath;

    @Value("${name.of.recipe.file}")
    private String dataFileName;

    public void saveToFile(String json) {
        try {
            Files.createDirectories(Path.of(dataFilePath));
            Path path = Path.of(dataFilePath).resolve(dataFileName);
            Files.writeString(path,json);
            cleanDataFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanDataFile()  {

        try {
            Files.deleteIfExists(Path.of(dataFilePath,dataFileName));
            Files.createFile(Path.of(dataFilePath,dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public File getRecipeFile(){
        return new File(dataFilePath + "/" + dataFileName);
    }
}
