package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class RankingController {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RankingController(LoveRepository loveRepository, RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/rank")
    public String getTopRecipes(Model model, @RequestParam(required = false) String categoryName) {
        //List<Love> topLove; // = loveRepository.findByOrderByLoveId();
        if (categoryName == null || categoryName.isEmpty()) {
        	List<Recipe> topLove = recipeRepository.findTop10ByOrderByTotalLoveDesc();
        	/*for (Recipe recipe : topLove) {
                System.out.println("Recipe ID: " + recipe.getRecipe_id());
                System.out.println("Recipe Title: " + recipe.getTitle());
                System.out.println("Total Love: " + recipe.getTotalLove());
            }
            */
        	model.addAttribute("topLove", topLove);
        	return "rank";
        } else {
            List<Recipe>topLove = recipeRepository.findBycategory_nameOrderByTotalLoveDesc(categoryName);
            model.addAttribute("topLove", topLove);
            model.addAttribute("selectedCategory", categoryName);
            return "rank";
        }

        

        
    }
}
