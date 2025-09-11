package com.firstsite.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    // главная страница
    @GetMapping("/")
    public String home(Model model) {
//        передаем данные внутрь шаблона
        model.addAttribute("name", "Главная страница");
        // вызываем шаблон по названию
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

}