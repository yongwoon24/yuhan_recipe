package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		 List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Order.desc("postId")));
		    model.addAttribute("boardList", boardList);
	        return "board"; // board.html 파일을 보여줄 뷰 이름
	    }
	
	@GetMapping("/createboard")
    public String showCreateBoardPage(HttpSession session, RedirectAttributes redirectAttributes) {
		String loggedInNickname = (String) session.getAttribute("loggedInNickname");
		 if (loggedInNickname != null) {
			 return "createboard"; // createboard.html 파일을 보여줄 뷰 이름
		 }
		 else {
			 redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!");
			 return "redirect:/login";
		 }
    }
	
	
	@GetMapping("/boardcontent/{postId}")
    public String viewBoard(@PathVariable Long postId, Model model) {
        // postId를 사용하여 보드 내용을 데이터베이스에서 가져온다.
        Optional<Board> optionalBoard = boardRepository.findById(postId);
        
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            System.out.println("Board Title: " + board.getTitle());
            System.out.println("Board Content: " + board.getContent());
            // 모델에 보드 내용을 추가하여 템플릿에서 사용할 수 있도록 한다.
            model.addAttribute("board", board);
            return "boardcontent"; // viewboard.html 템플릿으로 이동
        } else {
            // 보드가 존재하지 않을 경우 에러 처리
            return "board"; // 에러 페이지로 이동하거나 다른 처리를 수행
        }
    }
	
	
	@PostMapping("/createboard")
	public String createBoard(@ModelAttribute Board board, HttpSession session) {
		
	    // 현재 로그인한 사용자 정보 가져오기 (세션 활용)
	    String loggedInNickname = (String) session.getAttribute("loggedInNickname");

	    // 사용자 정보를 이용하여 작성자 정보 설정
	    board.setNickname(loggedInNickname);
	    
	    // 글을 작성한 날짜와 시간을 현재 시간으로 설정
	    LocalDateTime now = LocalDateTime.now().withNano(0);
	    board.setCreated_date(now);

	    // 게시글 저장
	    boardRepository.save(board);

	    return "redirect:/board"; // 글쓰기 성공 후 게시판 목록 페이지로 리다이렉트
	}
}


