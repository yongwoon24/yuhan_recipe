package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

@Controller
public class RecipeController {
     private final RecipeRepository recipeRepository;

        @Autowired
        public RecipeController(RecipeRepository recipeRepository) {
            this.recipeRepository = recipeRepository;
        }

        @GetMapping("/recipes")
        public String getAllRecipes(Model model) {
            List<Recipe> recipes = recipeRepository.findAll();
            model.addAttribute("recipes", recipes);
            return "recipeList"; // recipe.html을 렌더링하는 Thymeleaf 템플릿 이름
        }
    }