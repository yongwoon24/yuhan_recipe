package com.example.demo.controller;

import java.awt.Image;
import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Recipe_Ingredient;
import com.example.demo.entity.Step;
import com.example.demo.entity.User;
import com.example.demo.formdto.RecipeFormDto;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.LoveService;
import com.example.demo.service.RecipeService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class RecipeController {
	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private RecipeService recipeservice;
	@Autowired
	private LoveRepository loverepository;
	@Autowired
	private LoveService loveservice;
	
	List<Recipe> recipes1;
	private int likesCount = 0;

	@GetMapping("/recipe1")
	public String listRecipes(Model model, @RequestParam(required = false) String title) {
		List<Recipe> recipes = recipeRepository.findAll();
		model.addAttribute("recipes", recipes);
		return "recipeList";
	}

	@GetMapping("/recipe")
	public String listRecipes1(Model model, @RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false) String categorName) {
		// List<Recipe> categoryName;
		int pageSize = 20; // 페이지당 레시피 수
		// if(ca)

		List<Recipe> recipes = recipeRepository.findAll();
		model.addAttribute("recipes", recipes);
		model.addAttribute("currentPage", page);
		return "recipeList";
	}

	@PostMapping("/SearchRecipe")
	public String searchRecipes(Model model,
			@RequestParam(name = "categoryName", required = false) List<String> categories) {
		List<Recipe> recipes;
		if (categories != null && !categories.isEmpty()) {
			recipes = recipeRepository.findByCategoryNameIn(categories);
			recipes1 = recipes;
		} else {

			recipes = recipeRepository.findAll();

		}

		model.addAttribute("recipes", recipes);

		return "recipeList"; // 검색 결과를 표시할 뷰 이름
	}

	@GetMapping("/createRecipe")
	public String createRecipeForm(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "createRecipe";
	}

	@PostMapping("/createRecipe")
	@Async
	public String createRecipe(@ModelAttribute Recipe recipe, @ModelAttribute Recipe_Ingredient recipe_ingredient,
			@ModelAttribute Step step, MultipartFile file, HttpSession session,
			@RequestParam("ingredientName") List<String> ingredientName,
			@RequestParam("mensuration") List<String> mensuration, @RequestParam("SContent") List<String> SContent,
			@RequestParam("Singtxt") List<String> Singtxt, @RequestParam("Stooltxt") List<String> Stooltxt,
			@RequestParam("Stip") List<String> Stip, @RequestParam("Scontroltxt") List<String> Scontroltxt)
			throws Exception {
		String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		recipe.setNickname(loggedInNickname);
		recipeservice.write(recipe, file);
		recipeservice.createRecipe(recipe, ingredientName, mensuration);
		recipeservice.createStep(recipe, SContent, Singtxt, Stooltxt, Stip, Scontroltxt);
		recipeRepository.save(recipe);
		return "redirect:/recipe";
	}

//	    @PostMapping("/SearchRecipe")
//	    public String search(@RequestParam String category,)

	@PostMapping("/desc")
	public String descrecipe(Model model) {
		List<Recipe> recipes = recipeRepository.findAllByOrderByCreateddateDesc();

		model.addAttribute("recipes", recipes);
		return "recipeList";
	}
	
	@PostMapping("/VC")
	public String VCrecipe(Model model) {
		List<Recipe> recipes = recipeRepository.findAllByOrderByViewcountDesc();

		model.addAttribute("recipes", recipes);
		return "recipeList";
	}

	@PostMapping("/like")
	public String like(@RequestParam int recipe_id, HttpSession session, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String activity = "좋아요";
			Recipe recipe = new Recipe();
			recipe.setRecipe_id(recipe_id);

			User user = new User();
			String loggedInUserId = (String) session.getAttribute("loggedInUserId");
			user.setUser_id(loggedInUserId);

			// 사용자가 이미 좋아요를 눌렀는지 확인
			boolean userAlreadyLiked = checkIfUserLikedRecipe(user, recipe, session);

			if (!userAlreadyLiked) {
				Love love = new Love();
				love.setUser(user);
				love.setRecipe(recipe);
				love.setActivity(activity);

				loveservice.saveLove(love);

				// 사용자에게 쿠키 설정 (또는 세션 변수 설정)
				Cookie likeCookie = new Cookie("liked_recipe_" + recipe_id, "true");
				likeCookie.setMaxAge(60 * 60 * 24 * 30); // 쿠키의 유효 기간 설정 (예: 30일)
				response.addCookie(likeCookie);
			} else {
				// 이미 좋아요를 눌렀음을 사용자에게 알릴 수 있습니다.
				redirectAttributes.addFlashAttribute("errorMessage", "이미 좋아요 누르셨습니다");
			}
		} catch (Exception e) {
			// 예외가 발생하면 여기서 처리합니다.
			// 예를 들어, 데이터베이스 저장 중에 예외가 발생한 경우 사용자에게 알릴 수 있습니다.
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 해주세요");
		}

		return "redirect:/recipe/" + recipe_id;
	}

	// 사용자가 이미 좋아요를 눌렀는지 확인하는 메서드
	private boolean checkIfUserLikedRecipe(User user, Recipe recipe, HttpSession session) {
		// 여기에서 사용자와 레시피에 대한 좋아요 기록을 조회하여 확인합니다.
		int n = loverepository.countLovesByRecipeId1(recipe.getRecipe_id(), user);
		if (n > 0) {
			return true;
		}
		// 이미 좋아요를 누른 경우 true를 반환하고, 그렇지 않으면 false를 반환합니다.
		else {
			return false;
		}
	}

	@PostMapping("/increase_likes")
	@ResponseBody
	public int increaseLoves(@RequestParam("recipe_id") String recipe_id) {
		// 게시물 ID에 해당하는 좋아요 수 증가 로직
		likesCount++;
		return likesCount;
	}

	@GetMapping("/recipe/{recipe_id}")
	public String userRecipeview(@PathVariable("recipe_id") int recipe_id, Model model, HttpSession session) {
		Recipe recipe = recipeRepository.findById(recipe_id);
		if (recipe != null) {
			recipeRepository.incrementViewCount(recipe_id); // 조회수 업데이트

			String activity = "조회";
			Recipe recipe1 = new Recipe();
			recipe.setRecipe_id(recipe_id);

			User user = new User();
			String loggedInUserId = (String) session.getAttribute("loggedInUserId");
			if (loggedInUserId == null) {
				// 사용자가 로그인하지 않은 경우 로그인 페이지로 리다이렉트하거나 다른 처리를 수행합니다.
				user.setUser_id("guest");
				activity = "게스트조회";
				Love love = new Love();
				love.setUser(user);
				love.setRecipe(recipe);
				love.setActivity(activity);

				loveservice.saveLove(love);

				model.addAttribute("recipe", recipe);
				// model.addAttribute("likesCount",likesCount);
				// model.addAttribute("recipe", new Recipe());
				return "userRecipe"; // 레시피 페이지 템플릿
			} else {
				user.setUser_id(loggedInUserId);

				Love love = new Love();
				love.setUser(user);
				love.setRecipe(recipe);
				love.setActivity(activity);

				loveservice.saveLove(love);

				model.addAttribute("recipe", recipe);
				// model.addAttribute("likesCount",likesCount);
				// model.addAttribute("recipe", new Recipe());
				return "userRecipe"; // 레시피 페이지 템플릿
			}
		}
		return "userRecipe";
	}

	@GetMapping("/editRecipe/{recipe_id}")
	public String editRecipeForm(@PathVariable Integer recipe_id, Model model) {
		Recipe recipe = recipeRepository.findById(recipe_id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Recipe ID: " + recipe_id));
		model.addAttribute("recipe", recipe);
		return "editRecipe";
	}

	@PostMapping("/editRecipe/{recipe_id}")
	public String editRecipe(@PathVariable Long recipe_id, @ModelAttribute Recipe recipe) {
		recipeRepository.save(recipe);
		return "redirect:/recipe";
	}

	@GetMapping("/deleteRecipe/{recipe_id}")
	public String deleteRecipe(@PathVariable int recipe_id) {
		recipeservice.deletePostWithImage(recipe_id);
		return "redirect:/recipe";
	}

}
