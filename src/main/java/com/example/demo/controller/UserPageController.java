package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.RecipeRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserPageController {
	 private final BoardRepository boardRepository; // final 키워드 추가
	 private final RecipeRepository recipeRepository;
	 
	 @Autowired
	 public UserPageController(BoardRepository boardRepository, RecipeRepository recipeRepository) {
	       this.boardRepository = boardRepository;
	       this.recipeRepository = recipeRepository;
	   }
	   
	
	@GetMapping("/userpage_R/{nickname}")
	public String showUserPage(Model model, @PathVariable String nickname, @RequestParam(required = false, defaultValue = "1") int page) {
		boolean a = true;
		List<Recipe> recipe = recipeRepository.findByRecipeVerifiedAndNicknameOrderByCreateddateDesc(a, nickname);
		 
	       if (recipe != null) {
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
	   	    model.addAttribute("nickname", nickname);
	          return "userpage_R"; // mypage.html 파일을 보여줄 뷰 이름
	       }
	       else {

	          return "redirect:/login";
	       }
	}
	
	@GetMapping("/userpage_B/{nickname}")
	public String showUser_BPage(Model model, @PathVariable String nickname, @RequestParam(required = false, defaultValue = "0") int page) {
		List<Board> boardList = boardRepository.findByNicknameOrderByPostIdDesc(nickname);
	
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
		 model.addAttribute("nickname", nickname);
		 return "userpage_B";
	       
	}
	
}
