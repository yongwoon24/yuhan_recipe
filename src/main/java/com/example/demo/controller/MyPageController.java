package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {
 private final BoardRepository boardRepository; // final 키워드 추가
	 
	 @Autowired
	 public MyPageController(BoardRepository boardRepository) {
	       this.boardRepository = boardRepository;
	   }
	 
	 @GetMapping("/mypage")
	 public String showMyPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		 String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		 List<Board> board = boardRepository.findByNicknameOrderByPostIdDesc(loggedInNickname);
		 model.addAttribute("board", board);
	       if (loggedInNickname != null) {
	          return "mypage"; // mypage.html 파일을 보여줄 뷰 이름
	       }
	       else {
	          redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!");
	          return "redirect:/login";
	       }
		 
	 }
}
