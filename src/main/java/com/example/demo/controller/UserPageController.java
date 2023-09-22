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

@Controller
public class UserPageController {
	 private final BoardRepository boardRepository; // final 키워드 추가
	 private final RecipeRepository recipeRepository;
	 
	 @Autowired
	 public UserPageController(BoardRepository boardRepository, RecipeRepository recipeRepository) {
	       this.boardRepository = boardRepository;
	       this.recipeRepository = recipeRepository;
	   }
	   
	
	@GetMapping("/userpage/{nickname}")
	public String showUserPage(Model model, @PathVariable String nickname, @RequestParam(required = false, defaultValue = "0") int page) {
		List<Board> board = boardRepository.findByNicknameOrderByPostIdDesc(nickname);
		List<Recipe> recipe = recipeRepository.findByNickname(nickname);
	    model.addAttribute("board", board);
	    model.addAttribute("recipe", recipe);
	    
	    int pageSize = 10; // 페이지당 레시피 수
	    int startIndex = page * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, board.size());

	      List<Board> pagedBoards = board.subList(startIndex, endIndex);
	      model.addAttribute("board", pagedBoards);
	      model.addAttribute("currentPage", page);

	      // 전체 페이지 수 계산
	      int totalPageCount = (int) Math.ceil((double) board.size() / pageSize);
	      model.addAttribute("totalPageCount", totalPageCount);

	      // 첫 페이지 번호와 끝 페이지 번호 계산
	      int firstPage = 0;
	      int lastPage = totalPageCount - 1;
	      model.addAttribute("firstPage", firstPage);
	      model.addAttribute("lastPage", lastPage);
	    return "userpage";
	}
	
}
