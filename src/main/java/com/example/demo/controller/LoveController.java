package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Today;
import com.example.demo.entity.User;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.TodayRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoveController {
	@Autowired
    private LoveRepository loveRepository;
	@Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userR;
    @Autowired
    private TodayRepository todayR;

    
    
    boolean a = true;

    @GetMapping("/")
    public String getTopRecipes(Model model, @RequestParam(required = false) String category_name, HttpSession session) {
    	List<Recipe> topLove = recipeRepository.findTop10ByRecipeVerifiedOrderByTotalLoveDesc(a);
    	model.addAttribute("topLove", topLove);
    	
    	
    	String loggedInUserId = (String) session.getAttribute("loggedInUserId"); 
    	User user = userR.findByUser_id1(loggedInUserId);
    	
    	//오늘의 추천메뉴
    	List<Recipe> todayrecipes =new ArrayList<>();
    	Today today = todayR.findByUser(user);
    	todayrecipes.add(recipeRepository.findById(today.getNo1()));
    	todayrecipes.add(recipeRepository.findById(today.getNo2()));
    	todayrecipes.add(recipeRepository.findById(today.getNo3()));
    	todayrecipes.add(recipeRepository.findById(today.getNo4()));
    	todayrecipes.add(recipeRepository.findById(today.getNo5()));
    	todayrecipes.add(recipeRepository.findById(today.getNo6()));
    	todayrecipes.add(recipeRepository.findById(today.getNo7()));
    	todayrecipes.add(recipeRepository.findById(today.getNo8()));
    	todayrecipes.add(recipeRepository.findById(today.getNo9()));
    	todayrecipes.add(recipeRepository.findById(today.getNo10()));
    	
    	model.addAttribute("todayrecipes", todayrecipes);
    	
    	//최근본 레시피
    	List<Recipe> lastrecipes = loveRepository.findUserActivitiesWithdesc(user);
    	model.addAttribute("lastrecipes", lastrecipes);
    	
    	//유저랭킹
    	List<User> userRank = userR.findByOrderByUsertotallikes();
    	model.addAttribute("userRank",userRank);
    	
    	return "index2";

    }
}