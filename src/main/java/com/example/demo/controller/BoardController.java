package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class BoardController {
    private final BoardRepository boardRepository; // final 키워드 추가
    private final CommentRepository commentRepository;

   @Autowired
   public BoardController(BoardRepository boardRepository, CommentRepository commentRepository) {
       this.boardRepository = boardRepository;
       this.commentRepository = commentRepository;
   }
       
   @GetMapping("/board")
   public String showBoard(Model model,@RequestParam(required = false, defaultValue = "0") int page) {
	int pageSize = 10; // 페이지당 레시피 수  
	
	// 게시글 목록을 가져와서 모델에 추가
   List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Order.desc("postId")));
      model.addAttribute("boardList", boardList);
      
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
   
   @PostMapping("/createboard")
   public String createBoard(@ModelAttribute Board board, HttpSession session) {
      
       // 현재 로그인한 사용자 정보 가져오기 (세션 활용)
       String loggedInNickname = (String) session.getAttribute("loggedInNickname");
       String loggedInUserId = (String) session.getAttribute("loggedInUserId");

       // 사용자 정보를 이용하여 작성자 정보 설정
       board.setNickname(loggedInNickname);
       board.setUser_id(loggedInUserId);
       
       // 글을 작성한 날짜와 시간을 현재 시간으로 설정
       LocalDateTime now = LocalDateTime.now().withNano(0);
       board.setCreated_date(now);

       // 게시글 저장
       boardRepository.save(board);

       return "redirect:/board"; // 글쓰기 성공 후 게시판 목록 페이지로 리다이렉트
   }
   
   @GetMapping("/board/{postId}")
   public String showBoardContent(@PathVariable int postId, Model model) {
       Board board = boardRepository.findByPostId(postId);
       List<Comment> comment = commentRepository.findByPostId(postId);

       if (board == null) {
           // 게시물이 존재하지 않을 경우 에러 처리
           return "error"; // 에러 페이지로 이동하도록 변경하실 수 있습니다.
       }

       model.addAttribute("board", board);
       model.addAttribute("comment", comment);
       return "boardcontent"; // boardcontent.html로 이동
   }
   
	@GetMapping("/delete/{postId}")
	@Transactional // 트랜잭션 설정
	public String deleteBoard(@PathVariable int postId, RedirectAttributes redirectAttributes) {
		commentRepository.deleteByPostId(postId);
		boardRepository.deleteByPostId(postId);
		
		redirectAttributes.addFlashAttribute("boarddeleteMessage", "게시물 삭제가 완료되었습니다!");
	    return "redirect:/board"; // 글쓰기 성공 후 게시판 목록 페이지로 리다이렉트
	}
	
	@GetMapping("/edit/{postId}")
    public String showeditBoard(@PathVariable int postId, Model model) {
        // 게시글 목록을 가져와서 모델에 추가
		Board board = boardRepository.findByPostId(postId);
	    model.addAttribute("board", board);
        return "editboard";
    }
	
	@PostMapping("/edit/{postId}")
    public String editBoard(@PathVariable int postId, @ModelAttribute Board board2 ,RedirectAttributes redirectAttributes) {
	    Board board = boardRepository.findByPostId(postId);
	    board2.setCreated_date(board.getCreated_date());
	    board2.setUser_id(board.getUser_id());
	    board2.setNickname(board.getNickname());
	    boardRepository.save(board2);
	    redirectAttributes.addFlashAttribute("boardeditMessage", "게시물 수정이 완료되었습니다!");
	    return "redirect:/board/" + postId; // 글쓰기 성공 후 게시판 목록 페이지로 리다이렉트
    }
}

