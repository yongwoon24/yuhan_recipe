package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.RecipeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RecipeController {
	@Autowired
	 private RecipeRepository recipeRepository;
	RecipeService recipeservice;
	
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
	    
	    @PostMapping("/createRecipe1")
	    public String createUser1(@ModelAttribute Recipe recipe) {
	        recipeRepository.save(recipe);
	        return "redirect:/recipe";
	    }
	    //@ModelAttribute Recipe recipe
	    @PostMapping("/createRecipe")
	    public String createUser(@RequestPart(value="file",required = false)  MultipartFile files, @ModelAttribute Recipe recipe) throws IOException {
	        Recipe recipes = new Recipe();
	        
	        String sourceFileName = files.getOriginalFilename();
	        String sourceFileNameEx = FilenameUtils.getExtension(sourceFileName).toLowerCase();
	        FilenameUtils.removeExtension(sourceFileName);
	        
	        File destinationFile;
	        String destinationFileName;
    		String fileUrl = "D:/prioject2/src/main/resources/static/img";
    		
    		do { 
    			destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameEx; 
    			destinationFile = new File(fileUrl + destinationFileName); 
		} while (destinationFile.exists()); 

		destinationFile.getParentFile().mkdirs(); 
		files.transferTo(destinationFile);

		recipes.setMain_photo(destinationFileName);
		recipes.setMain_photo_oriname(sourceFileName);
		recipes.setMain_photo_url(fileUrl);
		recipeservice.save(recipes);
		recipeRepository.save(recipe);
	    	//recipeRepository.save(recipe);
	        return "redirect:/recipe1";
	    }
	
	    @PostMapping("/createRecipe3")
	    public String createUser3(@ModelAttribute Recipe recipe) {
	        recipeRepository.save(recipe);
	        return "redirect:/recipe";
	    }
	    
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

