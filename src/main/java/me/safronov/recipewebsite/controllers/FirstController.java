package me.safronov.recipewebsite.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String launchApplications() {
        return "Приложение запущено";
    }
    @GetMapping("info")
public String enterProjectInformation() {
        return " Слава. Сайт рецептов. 11.03.2023 На этом сайте вы найдете много необычных рецептов.";
}
}
