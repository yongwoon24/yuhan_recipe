package com.example.demo.controller;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Recipe_Ingredient;
import com.example.demo.entity.Scrap;
import com.example.demo.entity.Step;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;

import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.ScrapRepository;
import com.example.demo.repository.StepRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LoveService;
import com.example.demo.service.RecipeService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


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
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private ScrapRepository scraprepository;
	@Autowired
	private StepRepository steprepository;
	@Autowired
	private RecipeIngredientRepository recipeingredientrepository;
	@Autowired
	private TagRepository tagrepository;
	
	
	List<Recipe> recipes1;
	


	@GetMapping("/recipe")
	public String listRecipes2(Model model,
	        @RequestParam(name = "categoryName", required = false) List<String> categories,
	        @RequestParam(required = false, defaultValue = "1") int page, // 페이지 기본값을 1로 설정
	        @RequestParam(name = "ingredientNames", required = false) List<String> ingredientNames,
	        @RequestParam(name = "sort", required = false) String sort
	    ) {

		// categories를 적절하게 인코딩하여 URL에 포함
	    String encodedCategories = encodeCategories(categories);
	    String encodedingredientNames = encodeCategories(ingredientNames);
	    List<Recipe> recipes;
	    Boolean a = true;
	    
	    
	    
	 // 식별된 폼에 따라 작업 수행
        if ("latest".equals(sort)) {
            // submitForm 폼의 데이터 처리
        	if ((categories == null || categories.isEmpty()) && (ingredientNames == null || ingredientNames.isEmpty())) {
    	        // 카테고리와 재료 이름이 모두 제공되지 않은 경우 모든 레시피를 가져옵니다.
        		recipes = recipeRepository.findByRecipeVerifiedOrderByCreateddateDesc(a);
    	    } else {
    	        // 카테고리와 재료 이름 중 하나라도 제공된 경우 검색을 수행합니다.
    	        if (categories != null && !categories.isEmpty() && ingredientNames != null && !ingredientNames.isEmpty()) {
    	            // 카테고리와 재료 이름 모두 제공된 경우
    	        	recipes = recipeRepository.findByRecipeVerifiedAndCategoryNameInAndRecipeIngredientsIngredientIngredientNameInOrderByCreateddateDesc(a, categories, ingredientNames);
    	        } else if (categories != null && !categories.isEmpty()) {
    	            // 카테고리만 제공된 경우
    	        	recipes = recipeRepository.findByRecipeVerifiedAndCategoryNameInOrderByCreateddateDesc(a, categories);
    	        } else {
    	            // 재료 이름만 제공된 경우
    	        	recipes = recipeRepository.findByRecipeVerifiedAndRecipeIngredientsIngredientIngredientNameInOrderByCreateddateDesc(a, ingredientNames);
    	        }
    	    }
        } else if ("views".equals(sort)) {
            // VCdescForm 폼의 데이터 처리
        	if ((categories == null || categories.isEmpty()) && (ingredientNames == null || ingredientNames.isEmpty())) {
    	        // 카테고리와 재료 이름이 모두 제공되지 않은 경우 모든 레시피를 가져옵니다.
    	        recipes = recipeRepository.findByRecipeVerifiedOrderByViewcountDesc(a);
    	    } else {
    	        // 카테고리와 재료 이름 중 하나라도 제공된 경우 검색을 수행합니다.
    	        if (categories != null && !categories.isEmpty() && ingredientNames != null && !ingredientNames.isEmpty()) {
    	            // 카테고리와 재료 이름 모두 제공된 경우
    	            recipes = recipeRepository.findByRecipeVerifiedAndCategoryNameInAndRecipeIngredientsIngredientIngredientNameInOrderByViewcountDesc(a, categories, ingredientNames);
    	        } else if (categories != null && !categories.isEmpty()) {
    	            // 카테고리만 제공된 경우
    	            recipes = recipeRepository.findByRecipeVerifiedAndCategoryNameInOrderByViewcountDesc(a, categories);
    	        } else {
    	            // 재료 이름만 제공된 경우
    	            recipes = recipeRepository.findByRecipeVerifiedAndRecipeIngredientsIngredientIngredientNameInOrderByViewcountDesc(a, ingredientNames);
    	        }
        }
        }
        else {
        	if ((categories == null || categories.isEmpty()) && (ingredientNames == null || ingredientNames.isEmpty())) {
    	        // 카테고리와 재료 이름이 모두 제공되지 않은 경우 모든 레시피를 가져옵니다.
    	        recipes = recipeRepository.findByRecipeVerifiedOrderByCreateddateDesc(a);
    	    } else {
    	        // 카테고리와 재료 이름 중 하나라도 제공된 경우 검색을 수행합니다.
    	        if (categories != null && !categories.isEmpty() && ingredientNames != null && !ingredientNames.isEmpty()) {
    	            // 카테고리와 재료 이름 모두 제공된 경우
    	            recipes = recipeRepository.findByRecipeVerifiedAndCategoryNameInAndRecipeIngredientsIngredientIngredientNameInOrderByCreateddateDesc(a, categories, ingredientNames);
    	        } else if (categories != null && !categories.isEmpty()) {
    	            // 카테고리만 제공된 경우
    	            recipes = recipeRepository.findByRecipeVerifiedAndCategoryNameInOrderByCreateddateDesc(a, categories);
    	        } else {
    	            // 재료 이름만 제공된 경우
    	            recipes = recipeRepository.findByRecipeVerifiedAndRecipeIngredientsIngredientIngredientNameInOrderByCreateddateDesc(a, ingredientNames);
    	        }
    	    }
        }
	    
	    
	    
	    int totalRecipes = recipes.size();
	    int pageSize = 20; // 페이지당 레시피 수
	    int totalPages = (int) Math.ceil((double) totalRecipes / pageSize);
	    
	    // 현재 페이지가 유효한 범위 내에 있는지 확인
	    if (page < 1) {
	        page = 1;
	    } else if (page > totalPages) {
	        page = totalPages;
	    }

	    // 시작 인덱스와 끝 인덱스 계산
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, totalRecipes);
	    
	    // startIndex 및 endIndex 유효성 검사
	    if (startIndex < 0) {
	        startIndex = 0;
	    }
	    if (endIndex > totalRecipes) {
	        endIndex = totalRecipes;
	    }
	    
	    System.out.println(startIndex);
	    System.out.println(endIndex);
	    System.out.println(totalPages);

	    // 현재 페이지에 해당하는 레시피 목록 추출
	    List<Recipe> pagedRecipes = recipes.subList(startIndex, endIndex);

	    // 이전 페이지와 다음 페이지가 있는지 여부를 확인하여 모델에 추가
	    boolean hasPreviousPage = (page > 1);
	    boolean hasNextPage = (page < totalPages);
	    
	    model.addAttribute("recipes", pagedRecipes);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("hasPreviousPage", hasPreviousPage);
	    model.addAttribute("hasNextPage", hasNextPage);
	    model.addAttribute("categories", encodedCategories);
	    model.addAttribute("ingredientNames", encodedingredientNames);
	    model.addAttribute("sort", sort);
	    
	    System.out.println(categories);
	    System.out.println(encodedCategories);
	    return "recipeList";
        }
	
	
	
	
	private String encodeCategories(List<String> categories) {
	    if (categories == null || categories.isEmpty()) {
	        return "";
	    }

	    try {
	        StringBuilder encoded = new StringBuilder();
	        for (String category : categories) {
	            encoded.append(URLEncoder.encode(category, "UTF-8"));
	            encoded.append(",");
	        }
	        // 마지막 쉼표 제거
	        encoded.deleteCharAt(encoded.length() - 1);
	        return encoded.toString();
	    } catch (UnsupportedEncodingException e) {
	        // 처리할 수 없는 문자 인코딩 예외 처리
	        e.printStackTrace();
	        return "";
	    }
	}


	
	@GetMapping("/createRecipe")
	public String createRecipeForm(Model model,HttpSession session,RedirectAttributes redirectAttributes) {
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		if(loggedInUserId==null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 해주세요");
			return "redirect:/login";
		}else {
		model.addAttribute("recipe", new Recipe());
		return "createRecipe";}
	}

	@PostMapping("/createRecipe")
	@Async
	public String createRecipe(@ModelAttribute Recipe recipe, 
            @ModelAttribute Recipe_Ingredient recipe_ingredient,
            @ModelAttribute Step step, 
            @RequestParam("file") MultipartFile file, 
            HttpSession session,
            @RequestParam("ingredientName") List<String> ingredientName,
            @RequestParam("mensuration") List<String> mensuration, 
            @RequestParam("SContent") List<String> SContent,
            @RequestParam("Singtxt") List<String> Singtxt, 
            @RequestParam("Stooltxt") List<String> Stooltxt,
            @RequestParam("Stip") List<String> Stip, 
            @RequestParam("Scontroltxt") List<String> Scontroltxt,
            @RequestParam("file1") List<MultipartFile> file1,
            @RequestParam(name ="tags", required = false) String tags)
			throws Exception {
		recipe.setRecipeVerified(true);//레시피 검토 안해도 되도록 임시@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22
		String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		recipe.setNickname(loggedInNickname);
		recipeservice.write(recipe, file);
		recipeservice.createRecipe(recipe, ingredientName, mensuration);
		recipeservice.createStep(recipe, SContent, Singtxt, Stooltxt, Stip, Scontroltxt,file1);
		if(tags != null) {
			 List<String> tagList = Arrays.asList(tags.split(","));
			recipeservice.createtag(recipe, tagList);
		}
		
		
		//레시피 21개 복제 페이지네이션 테스트용
//		for (int i = 0; i < 21; i++) {
//			Recipe clonedRecipe = ctrlCRecipe(recipe,recipe_ingredient,step,ingredientName,mensuration,SContent,Singtxt,Stooltxt,Stip,Scontroltxt); // 레시피 복제
//	        recipeRepository.save(clonedRecipe);
//		}
		
		recipeRepository.save(recipe);
		return "redirect:/recipe";
	}
	
	
	//레시피 21개 복제메서드
	public Recipe ctrlCRecipe(Recipe recipe, Recipe_Ingredient recipe_ingredient,
			Step step,
			List<String> ingredientName,
			List<String> mensuration,List<String> SContent,
			List<String> Singtxt,List<String> Stooltxt,
			List<String> Stip,List<String> Scontroltxt) {
	Recipe clonedRecipe = new Recipe();
	clonedRecipe.setTitle(recipe.getTitle());
	clonedRecipe.setCategoryName(recipe.getCategoryName());
	clonedRecipe.setCreated_date(recipe.getCreated_date());
	clonedRecipe.setDailyLove(recipe.getDailyLove());
	clonedRecipe.setLoves(recipe.getLoves());
	clonedRecipe.setMain_photo(recipe.getMain_photo());
	clonedRecipe.setMain_photo_path(recipe.getMain_photo_path());
	clonedRecipe.setMonthlyLove(recipe.getMonthlyLove());
	clonedRecipe.setNickname(recipe.getNickname());
	clonedRecipe.setRecipeIngredients(recipe.getRecipeIngredients());
	clonedRecipe.setRecipesubtxt(recipe.getRecipesubtxt());
	clonedRecipe.setSteps(recipe.getSteps());
	clonedRecipe.setTotalLove(recipe.getTotalLove());
	clonedRecipe.setUser(recipe.getUser());
	clonedRecipe.setView_count(recipe.getView_count());
	clonedRecipe.setWeeklyLove(recipe.getWeeklyLove());
	clonedRecipe.setRecipe_id(recipe.getRecipe_id());
	recipeservice.createRecipe(clonedRecipe, ingredientName, mensuration);
	//recipeservice.createStep(clonedRecipe, SContent, Singtxt, Stooltxt, Stip, Scontroltxt,);
	
	
		//recipeRepository.save(recipe);
		return clonedRecipe;
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
	
	@PostMapping("/scap")
	public String Scap(@RequestParam int recipe_id, HttpSession session, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			 
			Recipe recipe = new Recipe();
			recipe.setRecipe_id(recipe_id);

			User user = new User();
			String loggedInUserId = (String) session.getAttribute("loggedInUserId");
			user.setUser_id(loggedInUserId);

			// 사용자가 이미 스크랩를 눌렀는지 확인
			boolean userAlreadyLiked = checkIfUserscapRecipe(user, recipe, session);

			if (!userAlreadyLiked) {
				Scrap scrap = new Scrap();
				scrap.setUser(user);
				scrap.setRecipe(recipe);
				//love.setActivity(activity);
				
				Love love = new Love();
				love.setUser(user);
				love.setRecipe(recipe);
				love.setActivity("스크랩");

				loveservice.saveLove(love);

				scraprepository.save(scrap);

				// 사용자에게 쿠키 설정 (또는 세션 변수 설정)
				Cookie likeCookie = new Cookie("liked_recipe_" + recipe_id, "true");
				likeCookie.setMaxAge(60 * 60 * 24 * 30); // 쿠키의 유효 기간 설정 (예: 30일)
				response.addCookie(likeCookie);
			} else {
				// 이미 좋아요를 눌렀음을 사용자에게 알릴 수 있습니다.
				redirectAttributes.addFlashAttribute("errorMessage", "이미 스크랩을 하셨습니다");
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
	
	// 사용자가 이미 스크랩를 눌렀는지 확인하는 메서드
		private boolean checkIfUserscapRecipe(User user, Recipe recipe, HttpSession session) {
			// 여기에서 사용자와 레시피에 대한 좋아요 기록을 조회하여 확인합니다.
			int n = scraprepository.countscrapByRecipeId(recipe.getRecipe_id(), user.getUser_id());
			if (n > 0) {
				return true;
			}
			// 이미 좋아요를 누른 경우 true를 반환하고, 그렇지 않으면 false를 반환합니다.
			else {
				return false;
			}
		}

	

	@GetMapping("/recipe/{recipe_id}")
	public String userRecipeview(@PathVariable("recipe_id") int recipe_id, Model model, HttpSession session) {
		Recipe recipe = recipeRepository.findById(recipe_id);
		String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		String Nickname = recipe.getNickname();
		
		
		if (recipe != null) {
			recipeRepository.incrementViewCount(recipe_id); // 조회수 업데이트
			List<Recipe_Ingredient> recipeing = recipe.getRecipeIngredients();
			List<Step> steps = recipe.getSteps();
			List<Tag> tagss = recipe.getTag();

			
			String activity = "조회";
			recipe.setRecipe_id(recipe_id);

			User user = new User();
			String loggedInUserId = (String) session.getAttribute("loggedInUserId");
			String nickname = recipe.getNickname();
			String photo = userrepository.findbynickname(nickname).getUserphotopath();
			if(photo == null) {
				photo="/img/기본유저1.jpg";
			}
			String pr = userrepository.findbynickname(nickname).getUserpr();
			if (loggedInUserId == null) {
				// 사용자가 로그인하지 않은 경우 로그인 페이지로 리다이렉트하거나 다른 처리를 수행합니다.
				user.setUser_id("guest");
				activity = "게스트조회";
				Love love = new Love();
				love.setUser(user);
				love.setRecipe(recipe);
				love.setActivity(activity);

				//loveservice.saveLove(love);
				model.addAttribute("pr",pr);
				model.addAttribute("photo",photo);
				model.addAttribute("recipeings",recipeing);
				model.addAttribute("steps",steps);
				model.addAttribute("tagss",tagss);
				model.addAttribute("recipe", recipe);
				model.addAttribute("loggedInNickname", loggedInNickname);
				model.addAttribute("Nickname", Nickname);
				return "userRecipe"; // 레시피 페이지 템플릿
			} else {
				
				user.setUser_id(loggedInUserId);
				
				Love love = new Love();
				love.setUser(user);
				love.setRecipe(recipe);
				love.setActivity(activity);

				loveservice.saveLove(love);
				
				model.addAttribute("pr",pr);
				model.addAttribute("photo",photo);
				model.addAttribute("recipeings",recipeing);
				model.addAttribute("steps",steps);
				model.addAttribute("tagss",tagss);
				model.addAttribute("recipe", recipe);
				model.addAttribute("loggedInNickname", loggedInNickname);
				model.addAttribute("Nickname", Nickname);
				return "userRecipe"; // 레시피 페이지 템플릿
			}
		}
		return "userRecipe";
	}

	@GetMapping("/editRecipe/{recipe_id}")
	public String editRecipeForm(@PathVariable Integer recipe_id, Model model,
			HttpSession session, RedirectAttributes redirectAttributes) {
		String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		Recipe recipe = recipeRepository.findById(recipe_id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Recipe ID: " + recipe_id));
		List<Recipe_Ingredient> recipeing = recipe.getRecipeIngredients();
		List<Step> steps = recipe.getSteps();
		int stepssize = steps.size();
		List<Tag> tagss = recipe.getTag();
		int tagsize = tagss.size();
		model.addAttribute("stepssize",stepssize);
		model.addAttribute("tagsize",tagsize);
		model.addAttribute("steps",steps);
		model.addAttribute("tagss",tagss);
		model.addAttribute("recipeings",recipeing);
		model.addAttribute("recipe", recipe);
		return "editRecipe";
	}

	@PostMapping("/editRecipe/{recipe_id}")
	public String editRecipe(@PathVariable int recipe_id ,@ModelAttribute Recipe recipe,  
			@RequestParam("SContent") List<String> SContent,
            @RequestParam("Singtxt") List<String> Singtxt, 
            @RequestParam("Stooltxt") List<String> Stooltxt,
            @RequestParam("Stip") List<String> Stip, 
            @RequestParam("Scontroltxt") List<String> Scontroltxt,
            @RequestParam("file1") List<MultipartFile> file1,
            @RequestParam("ingredientName") List<String> ingredientName,
            @RequestParam("mensuration") List<String> mensuration,
            @RequestParam(name ="tags", required = false) String tags,
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "deleteindex", required = false) List<Integer> deleteindex) throws Exception {
	    recipe.setRecipe_id(recipe_id); // Set the ID to the path variable value for updating the correct recipe
	    Recipe recipe1 = recipeRepository.findById(recipe_id);
	    String nickname = recipe1.getNickname();
	    recipe.setNickname(nickname);
	    recipe = recipe1;
	    
	    String Rphoto = recipe1.getMain_photo();
	    String Rphotopath = recipe1.getMain_photo_path();
	    if(Rphoto == null) {
	    	Rphoto = "";
	    	Rphotopath = "";
	    }
	    recipeservice.editwrite(recipe, file, Rphoto,Rphotopath);
	    
	    List<Love> loves = loverepository.findByRecipe(recipe1);
	    recipe.setLoves(loves);
	    
	    List<Scrap> scraps = scraprepository.findByRecipe(recipe1);
	    recipe.setScraps(scraps);
	    
	    List<Step> evsteps = steprepository.findByRecipe(recipe1);
	    if(deleteindex != null) {
	    for (int i = 0; i < deleteindex.size(); i++) {
			steprepository.delete(evsteps.get(deleteindex.get(i)-1));
		}}
	    List<String> photo = new ArrayList<>();
	    List<String> photopath = new ArrayList<>();
	    for (int i = 0; i < evsteps.size(); i++) {
	    	photo.add(evsteps.get(i).getSphoto());
	    	photopath.add(evsteps.get(i).getSphotopath());
		}
	    
	    recipeservice.editStep1(recipe, SContent, Singtxt, Stooltxt, Stip, Scontroltxt,file1);
	    steprepository.deleteAll(evsteps);
	    
	    List<Recipe_Ingredient> evrecipeings = recipeingredientrepository.findByRecipe(recipe1);
	    recipeingredientrepository.deleteAll(evrecipeings);
	    recipeservice.createRecipe(recipe, ingredientName, mensuration);
	    
	    List<Tag> evtags = tagrepository.findByRecipe(recipe1);
	    tagrepository.deleteAll(evtags);
	    if(tags != null) {
			 List<String> tagList = Arrays.asList(tags.split(","));
			recipeservice.createtag(recipe, tagList);
		}
	    
	    
	    recipeRepository.save(recipe);
	    return "redirect:/recipe/{recipe_id}"; // Redirect to the recipe page
	}
	
	
	@GetMapping("/deleteRecipe/{recipe_id}")
	public String deleteRecipe(@PathVariable int recipe_id, RedirectAttributes redirectAttributes) {		
		 recipeservice.deletePostWithImage(recipe_id);
		 redirectAttributes.addFlashAttribute("asd", "삭제되었습니다");
				
		return "redirect:/recipe";
	}
	
	
	
	@GetMapping("/search")
	public String searchRecipes(Model model,
	        @RequestParam(required = false, defaultValue = "1") int page, // 페이지 기본값을 1로 설정
	        @RequestParam(required = false) String keyword,
	        @RequestParam(name = "sort", required = false) String sort
	    ) {
	
	    List<Recipe> recipes;
	    boolean b = true;
	    
	    
	    if (keyword != null && !keyword.isEmpty()) {
	    	
	        if ("latest".equals(sort)) {
	        	recipes = recipeRepository.findByTitleContainingAndRecipeVerifiedOrTagContentAndRecipeVerified(keyword,b, keyword, Sort.by(Sort.Order.desc("createddate")), b);
	        } else if ("views".equals(sort)) {
	        	recipes = recipeRepository.findByTitleContainingAndRecipeVerifiedOrTagContentAndRecipeVerified(keyword, b, keyword, Sort.by(Sort.Order.desc("viewcount")), b);
	        } else {
	        	recipes = recipeRepository.findByTitleContainingAndRecipeVerifiedOrTagContentAndRecipeVerified(keyword,b, keyword, Sort.by(Sort.Order.desc("createddate")), b);
	        }
	    } else {
	    	recipes = recipeRepository.findByTitleContainingAndRecipeVerifiedOrTagContentAndRecipeVerified(keyword,b, keyword, Sort.by(Sort.Order.desc("createddate")), b);
	    }
	    
	    
	    
	    int totalRecipes = recipes.size();
	    int pageSize = 20; // 페이지당 레시피 수
	    int totalPages = (int) Math.ceil((double) totalRecipes / pageSize);
	    
	    // 현재 페이지가 유효한 범위 내에 있는지 확인
	    if (page < 1) {
	        page = 1;
	    } else if (page > totalPages) {
	        page = totalPages;
	    }

	    // 시작 인덱스와 끝 인덱스 계산
	    int startIndex = (page - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, totalRecipes);
	    
	    // startIndex 및 endIndex 유효성 검사
	    if (startIndex < 0) {
	        startIndex = 0;
	    }
	    if (endIndex > totalRecipes) {
	        endIndex = totalRecipes;
	    }
	    
	    System.out.println(startIndex);
	    System.out.println(endIndex);
	    System.out.println(totalPages);

	    // 현재 페이지에 해당하는 레시피 목록 추출
	    List<Recipe> pagedRecipes = recipes.subList(startIndex, endIndex);

	    // 이전 페이지와 다음 페이지가 있는지 여부를 확인하여 모델에 추가
	    boolean hasPreviousPage = (page > 1);
	    boolean hasNextPage = (page < totalPages);
	    
	    model.addAttribute("recipes", pagedRecipes);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("hasPreviousPage", hasPreviousPage);
	    model.addAttribute("hasNextPage", hasNextPage);
	    model.addAttribute("sort", sort);
	    model.addAttribute("keyword", keyword); // 검색어 추가
	    
	    return "search";
        }

}