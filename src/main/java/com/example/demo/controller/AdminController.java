package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
@Controller
public class AdminController {
	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LoveRepository loveRepository;
	boolean a = false;
	
	@GetMapping("/reviewRecipes")
    public String lstRecipe(Model model, HttpSession session) {
       String loggedInNickname = (String) session.getAttribute("loggedInNickname");
       if("관리자".equals(loggedInNickname)) {
           List<Recipe> recipes = recipeRepository.findByRecipeVerifiedOrderByCreateddateDesc(a);
           model.addAttribute("recipes", recipes);
           return "recipemanage";
       }else {
          return "redirect:/";
       }
    }
	
	@PostMapping("/a/{recipe_id}")
	public String zz(@PathVariable int recipe_id,HttpSession session) {
		String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		User user = userRepository.findbynickname(loggedInNickname);
		Recipe recipe = recipeRepository.findById(recipe_id);
		recipe.setRecipeVerified(true);
		recipeRepository.save(recipe);
		Love love = new Love();
		love.setUser(user);
		love.setRecipe(recipe);
		love.setActivity("레시피 승인");
		loveRepository.save(love);
		return "redirect:/";
	}
}
