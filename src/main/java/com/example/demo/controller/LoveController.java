package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.LoveRepository;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoveController {

    private final LoveRepository loveRepository;

    @Autowired
    public LoveController(LoveRepository loveRepository) {
        this.loveRepository = loveRepository;
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
        
        model.addAttribute("recipeLikeCounts", recipeLikeCounts);
        model.addAttribute("sortedRecipeLikeCounts", sortedRecipeLikeCounts);
        model.addAttribute("top10RecipeLikeCounts", top10RecipeLikeCounts);
        return "test"; // return the name of your HTML template
    }
}