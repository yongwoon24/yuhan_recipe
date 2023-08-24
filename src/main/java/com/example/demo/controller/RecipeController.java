package com.example.demo.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Recipe;
import com.example.demo.formdto.RecipeFormDto;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.RecipeService;


import jakarta.validation.Valid;

@Controller
public class RecipeController {
	@Autowired
	 private RecipeRepository recipeRepository;
	private RecipeService recipeservice;
	
	@GetMapping("/recipe")
	public String listRecipes(Model model) {
		List<Recipe> recipes = recipeRepository.findAll();
		model.addAttribute("recipes", recipes);
		return "recipeList2";
	}
	@GetMapping("/recipe1")
	public String listRecipes1(Model model) {
		List<Recipe> recipes = recipeRepository.findAll();
		model.addAttribute("recipes", recipes);
		return "recipeList";
	}
	 @GetMapping("/createRecipe")
	    public String createRecipeForm(Model model) {
	        model.addAttribute("recipe", new Recipe());
	        return "createRecipe";
	    }
	    
	    @PostMapping("/createRecipe")
	    public String createRecipe(@Valid RecipeFormDto recipeFormDto, BindingResult bindingResult, Model model,
	    		                   @RequestParam(name = "main_photo") List<MultipartFile> file) {
	    	if (bindingResult.hasErrors()) {
	            return "redirect:/recipe";
	        }
	    	
	    	try {
	            //recipeservice.saveRecipe(recipeFormDto, file);
	        } catch (Exception e) {
	            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
	            return "redirect:/recipe";
	        }
	        return "redirect:/recipe";}
	    
	    
	    
	    
	    
	    
	    
	    @GetMapping("/editRecipe/{recipe_id}")
        public String editRecipeForm(@PathVariable Long recipe_id, Model model) {
            Recipe recipe = recipeRepository.findById(recipe_id).orElseThrow(() -> new IllegalArgumentException("Invalid Recipe ID: " + recipe_id));
            model.addAttribute("recipe", recipe);
            return "editRecipe";
        }

        @PostMapping("/editRecipe/{recipe_id}")
        public String editRecipe(@PathVariable Long recipe_id, @ModelAttribute Recipe recipe) {
            recipeRepository.save(recipe);
            return "redirect:/recipe";
        }
        
        @GetMapping("/deleteRecipe/{recipe_id}")
        public String deleteRecipe(@PathVariable Long recipe_id) {
            recipeRepository.deleteById(recipe_id);
            return "redirect:/recipe";
        }
        
       
	}

