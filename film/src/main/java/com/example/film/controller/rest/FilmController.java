package com.example.film.controller.rest;

import com.example.film.service.category.CategoryService;
import com.example.film.service.person.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class FilmController {

    private final PersonService personService;
    private final CategoryService categoryService;


    @GetMapping
    public String index(Model model){
        model.addAttribute("categories", categoryService.getPersonForSelect());
        model.addAttribute("directors", personService.getPersonsByRole("Director"));
        model.addAttribute("actors", personService.getPersonsByRole("Actor"));
        return "demo";
    }
}
