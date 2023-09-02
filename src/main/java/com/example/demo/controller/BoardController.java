package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	 private final BoardRepository boardRepository; // final 키워드 추가

	@Autowired
	public BoardController(BoardRepository boardRepository) {
	    this.boardRepository = boardRepository;
	}
	    
	 @GetMapping("/board")
	    public String showBoard(Model model) {
	        // 게시글 목록을 가져와서 모델에 추가
		 List<Board> boardList = boardRepository.findAll();
	        model.addAttribute("boardList", boardList);
	        return "board"; // board.html 파일을 보여줄 뷰 이름
	    }
	
	@GetMapping("/createboard")
    public String showCreateBoardPage() {
        return "createboard"; // createboard.html 파일을 보여줄 뷰 이름
    }
	
	@PostMapping("/createboard")
	public String createBoard(@ModelAttribute Board board, HttpSession session) {
		
	    // 현재 로그인한 사용자 정보 가져오기 (세션 활용)
	    String loggedInUserId = (String) session.getAttribute("loggedInUserId");

	    // 사용자 정보를 이용하여 작성자 정보 설정
	    board.setUser_id(loggedInUserId);
	    
	    // 글을 작성한 날짜와 시간을 현재 시간으로 설정
	    LocalDateTime now = LocalDateTime.now().withNano(0);
	    board.setCreated_date(now);

	    // 게시글 저장
	    boardRepository.save(board);

	    return "redirect:/board"; // 글쓰기 성공 후 게시판 목록 페이지로 리다이렉트
	}
}
