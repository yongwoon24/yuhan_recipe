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
    public String getTopRecipes(Model model, @RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false) String categoryName) {
        int pageSize = 20; // 페이지당 레시피 수

        List<Recipe> topLove;
        if (categoryName == null || categoryName.isEmpty()) {
            topLove = recipeRepository.findByOrderByTotalLoveDesc();
        } else {
            topLove = recipeRepository.findByCategoryNameOrderByTotalLoveDesc(categoryName);
            model.addAttribute("selectedCategory", categoryName);
        }

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, topLove.size());

        List<Recipe> pagedRecipes = topLove.subList(startIndex, endIndex);
        model.addAttribute("topLove", pagedRecipes);
        model.addAttribute("currentPage", page);

        return "rank";
    }
}
