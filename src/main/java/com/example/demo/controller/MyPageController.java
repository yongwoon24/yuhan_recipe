package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Board;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RecipeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {
 private final BoardRepository boardRepository; // final 키워드 추가
 private final RecipeRepository recipeRepository;
 private final UserRepository userRepository;
 private final RecipeService recipeservice;
	 
	 @Autowired
	 public MyPageController(BoardRepository boardRepository, RecipeRepository recipeRepository, UserRepository userRepository
			 , RecipeService recipeservice) {
	       this.boardRepository = boardRepository;
	       this.recipeRepository = recipeRepository;
	       this.userRepository = userRepository;
	       this.recipeservice=recipeservice;
	   }
	 
	 @GetMapping("/mypage")
	 public String showMyPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		 
		 String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		 String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		 if(loggedInNickname == null) {redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!"); return "redirect:/login";}
		 User user = userRepository.findbynickname(loggedInNickname);
		 String name = user.getName();
		 String email= user.getEmail();
		 String phone = user.getPhone_number();
		 String photo = user.getUserphotopath();
		 String pr = user.getUserpr();
		 LocalDate birthdate = user.getBirthdate();
		 List<Board> board = boardRepository.findByNicknameOrderByPostIdDesc(loggedInNickname);
		 List<Recipe> recipe = recipeRepository.findByNicknameOrderByCreateddateDesc(loggedInNickname);
		 model.addAttribute("board", board);
		 model.addAttribute("recipe", recipe);
		 model.addAttribute("user_id",loggedInUserId);
		 model.addAttribute("nickname",loggedInNickname);
		 model.addAttribute("name", name);
		 model.addAttribute("email", email);
		 model.addAttribute("phone", phone);
		 model.addAttribute("birthdate", birthdate);
		 model.addAttribute("photo", photo);
		 model.addAttribute("pr", pr);
		 return "mypage_U";
	 }
	 
	 @PostMapping("/mypage")
	 public String changeMyPage(Model model, HttpSession session, RedirectAttributes redirectAttributes,
			 @RequestParam(name = "password") String password,
	 		 @RequestParam(name = "newpassword") String newpassword,
			 @RequestParam(name = "confirmnewpassword") String confirmnewpassword,
			 @RequestParam(name = "pr") String pr
			 , @RequestParam("file") MultipartFile file) throws Exception {
		 String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		 User user = userRepository.findbynickname(loggedInNickname);
		 if(!file.isEmpty()) {
		 recipeservice.userwrite(user, file);}
		 user.setUserpr(pr);
		 if(password.equals(user.getPassword())) {
			 if(newpassword.equals(confirmnewpassword)) {
				 user.setPassword(newpassword);
				 
				 userRepository.save(user);
				 redirectAttributes.addFlashAttribute("errorMessage3", "회원정보 수정이 완료되었습니다!");
			 }else {
				 redirectAttributes.addFlashAttribute("errorMessage2", "새 비밀번호가 일치하지 않습니다!");
				 return "redirect:/mypage";
			 }
		 }else {
			 redirectAttributes.addFlashAttribute("errorMessage1", "현재 비밀번호가 일치하지 않습니다!");
			 return "redirect:/mypage";
			 }
		 
	       return "redirect:/mypage";
	 }
	 
	 @GetMapping("/mypage_B")
	 public String showMyPage1(Model model, HttpSession session, RedirectAttributes redirectAttributes,@RequestParam(required = false, defaultValue = "0") int page) {
		 String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		 List<Board> boardList = boardRepository.findByNicknameOrderByPostIdDesc(loggedInNickname);
		 List<Recipe> recipe = recipeRepository.findByNicknameOrderByCreateddateDesc(loggedInNickname);
		 int pageSize = 20; // 페이지당 레시피 수 
		 int startIndex = page * pageSize;
		    int endIndex = Math.min(startIndex + pageSize, boardList.size());

		    List<Board> pagedBoards = boardList.subList(startIndex, endIndex);
		    model.addAttribute("boardList", pagedBoards);
		    model.addAttribute("currentPage", page);


		    // 전체 페이지 수 계산
		    int totalPageCount = (int) Math.ceil((double) boardList.size() / pageSize);
		    model.addAttribute("totalPageCount", totalPageCount);

		    // 첫 페이지 번호와 끝 페이지 번호 계산
		    int firstPage = 0;
		    int lastPage = totalPageCount - 1;
		    model.addAttribute("firstPage", firstPage);
		    model.addAttribute("lastPage", lastPage);
		 model.addAttribute("board", pagedBoards);
		 model.addAttribute("recipe", recipe);
	       if (loggedInNickname != null) {
	          return "mypage_B"; // mypage.html 파일을 보여줄 뷰 이름
	       }
	       else {
	          redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!");
	          return "redirect:/login";
	       }
		 
	 }
	 
	 @GetMapping("/mypage_R")
	 public String showMyPage2(Model model, HttpSession session, RedirectAttributes redirectAttributes,
			 @RequestParam(required = false, defaultValue = "1") int page) {
		 String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		 List<Recipe> recipe = recipeRepository.findByNicknameOrderByCreateddateDesc(loggedInNickname);
		 
	       if (loggedInNickname != null) {
	    	int totalRecipes = recipe.size();
	   	    int pageSize = 20; // 페이지당 레시피 수
	   	    int totalPages = (int) Math.ceil((double) totalRecipes / pageSize);
	   	    System.out.println(totalPages);
	   	    System.out.println(totalRecipes);
	   	    
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
	   	    


	   	    // 현재 페이지에 해당하는 레시피 목록 추출
	   	    List<Recipe> pagedRecipes = recipe.subList(startIndex, endIndex);

	   	    // 이전 페이지와 다음 페이지가 있는지 여부를 확인하여 모델에 추가
	   	    boolean hasPreviousPage = (page > 0);
	   	    boolean hasNextPage = (page < totalPages);
	   	    
	   	    int firstPage = 1;
	        int lastPage = totalPages;
	        model.addAttribute("firstPage", firstPage);
	        model.addAttribute("lastPage", lastPage);
	   	    
	   	    model.addAttribute("recipes", pagedRecipes);
	   	    model.addAttribute("currentPage", page);
	   	    model.addAttribute("totalPages", totalPages);
	   	    model.addAttribute("hasPreviousPage", hasPreviousPage);
	   	    model.addAttribute("hasNextPage", hasNextPage);
	          return "mypage_R"; // mypage.html 파일을 보여줄 뷰 이름
	       }
	       else {
	          redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!");
	          return "redirect:/login";
	       }
		 
	 }
	 
	 @GetMapping("/mypage_L")
	 public String showMyPage3(Model model, HttpSession session, RedirectAttributes redirectAttributes,
			 @RequestParam(required = false, defaultValue = "1") int page) {
		 String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		 String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		 List<Recipe> recipe = recipeRepository.findRecipesLikedByUser(loggedInUserId);
		 
	       if (loggedInNickname != null) {
	    	int totalRecipes = recipe.size();
	   	    int pageSize = 20; // 페이지당 레시피 수
	   	    int totalPages = (int) Math.ceil((double) totalRecipes / pageSize);
	   	    System.out.println(totalPages);
	   	    System.out.println(totalRecipes);
	   	    
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
	   	    


	   	    // 현재 페이지에 해당하는 레시피 목록 추출
	   	    List<Recipe> pagedRecipes = recipe.subList(startIndex, endIndex);

	   	    // 이전 페이지와 다음 페이지가 있는지 여부를 확인하여 모델에 추가
	   	    boolean hasPreviousPage = (page > 0);
	   	    boolean hasNextPage = (page < totalPages);
	   	    
	   	    int firstPage = 1;
	        int lastPage = totalPages;
	        model.addAttribute("firstPage", firstPage);
	        model.addAttribute("lastPage", lastPage);
	   	    
	   	    model.addAttribute("recipes", pagedRecipes);
	   	    model.addAttribute("currentPage", page);
	   	    model.addAttribute("totalPages", totalPages);
	   	    model.addAttribute("hasPreviousPage", hasPreviousPage);
	   	    model.addAttribute("hasNextPage", hasNextPage);
	          return "mypage_L"; // mypage.html 파일을 보여줄 뷰 이름
	       }
	       else {
	          redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!");
	          return "redirect:/login";
	       }
	 }
	 @GetMapping("/mypage_S")
	 public String showMyPage4(Model model, HttpSession session, RedirectAttributes redirectAttributes,
			 @RequestParam(required = false, defaultValue = "1") int page) {
		 String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		 String loggedInUserId = (String) session.getAttribute("loggedInUserId");
		 List<Recipe> recipe = recipeRepository.findRecipesScrapByUser(loggedInUserId);
		 
	       if (loggedInNickname != null) {
	    	int totalRecipes = recipe.size();
	   	    int pageSize = 20; // 페이지당 레시피 수
	   	    int totalPages = (int) Math.ceil((double) totalRecipes / pageSize);
	   	    System.out.println(totalPages);
	   	    System.out.println(totalRecipes);
	   	    
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
	   	    


	   	    // 현재 페이지에 해당하는 레시피 목록 추출
	   	    List<Recipe> pagedRecipes = recipe.subList(startIndex, endIndex);

	   	    // 이전 페이지와 다음 페이지가 있는지 여부를 확인하여 모델에 추가
	   	    boolean hasPreviousPage = (page > 0);
	   	    boolean hasNextPage = (page < totalPages);
	   	    
	   	    int firstPage = 1;
	        int lastPage = totalPages;
	        model.addAttribute("firstPage", firstPage);
	        model.addAttribute("lastPage", lastPage);
	   	    
	   	    model.addAttribute("recipes", pagedRecipes);
	   	    model.addAttribute("currentPage", page);
	   	    model.addAttribute("totalPages", totalPages);
	   	    model.addAttribute("hasPreviousPage", hasPreviousPage);
	   	    model.addAttribute("hasNextPage", hasNextPage);
	          return "mypage_S"; // mypage.html 파일을 보여줄 뷰 이름
	       }
	       else {
	          redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!");
	          return "redirect:/login";
	       }
	 }
}
