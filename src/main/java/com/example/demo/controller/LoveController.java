package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoveController {

    private final LoveRepository loveRepository;
	private final RecipeRepository recipeRepository;

    @Autowired
    public LoveController(LoveRepository loveRepository, RecipeRepository recipeRepository) {
        this.loveRepository = loveRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/top-recipes")
    public String getTopRecipes(Model model) {
        // Get the list of top 10 liked recipes
        List<Love> topLove = loveRepository.findByOrderByLoveId();
        
        // Create a map to store recipe IDs and their corresponding like counts
        Map<Long, Long> recipeLikeCounts = topLove.stream()
                .collect(Collectors.groupingBy(
                        love -> (long)love.getRecipe().getRecipe_id(),
                        Collectors.counting()
                ));
        
     // Sort the map by value (like counts) in descending order
        Map<Long, Long> sortedRecipeLikeCounts = recipeLikeCounts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        
        Map<Long, Long> top10RecipeLikeCounts = sortedRecipeLikeCounts.entrySet().stream()
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        
        Map<Long, String> recipeTitles = top10RecipeLikeCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> recipeRepository.findTitleByRecipeId(entry.getKey())
                ));
        Map<Long, String> recipeImages = top10RecipeLikeCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> "img/" + entry.getKey() // Assuming image file names are based on recipe IDs
                ));
        //model.addAttribute("recipeLikeCounts", recipeLikeCounts);
        //model.addAttribute("sortedRecipeLikeCounts", sortedRecipeLikeCounts);
        model.addAttribute("top10RecipeLikeCounts", top10RecipeLikeCounts);
        model.addAttribute("recipeTitles", recipeTitles);
        model.addAttribute("recipeImages", recipeImages);

        return "test"; // return the name of your HTML template
        
        
    }    
}