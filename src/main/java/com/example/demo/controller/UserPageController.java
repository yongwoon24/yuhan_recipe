package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;

@Controller
public class UserPageController {
	 private final BoardRepository boardRepository; // final 키워드 추가
	 
	 @Autowired
	 public UserPageController(BoardRepository boardRepository) {
	       this.boardRepository = boardRepository;
	   }
	   
	
	@GetMapping("/userpage/{nickname}")
	public String showUserPage(Model model, @PathVariable String nickname) {
		List<Board> board = boardRepository.findByNicknameOrderByPostIdDesc(nickname);
	    model.addAttribute("board", board);
	    return "userpage";
	}
	
}
