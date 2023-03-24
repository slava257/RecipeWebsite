package me.safronov.recipewebsite.services.impl;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
public class FilesServicesImpl {
    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.data.file}")
    private String dataFileName;

    public void saveToFile(String json ) {
        try {
            Files.createDirectories(Path.of(dataFilePath));
            Path path = Path.of(dataFilePath).resolve(dataFileName);
            cleanDataFile(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void cleanDataFile(Path path,String json)  {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
