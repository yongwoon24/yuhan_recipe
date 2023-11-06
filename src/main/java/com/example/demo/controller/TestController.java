package com.example.demo.controller;

import com.example.demo.entity.Recipe;
import com.example.demo.entity.Love;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.LoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class TestController {

    private final RecipeRepository recipeRepository;
    private final LoveRepository userActivityRepository;

    @Autowired
    public TestController(RecipeRepository recipeRepository, LoveRepository userActivityRepository) {
        this.recipeRepository = recipeRepository;
        this.userActivityRepository = userActivityRepository;
    }

    @GetMapping("/test")
    public String test() {
       

        return "test";
    }
    @GetMapping("/test1")
    public String test1() {
       

        return "test1";
    }
}
