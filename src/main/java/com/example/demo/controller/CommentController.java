package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.RecipeComment;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeCommentRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
@Controller
public class CommentController {
    private final CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardrepository;
    @Autowired
    private UserRepository userrepository;
    @Autowired
    private LoveRepository loverepository;
    @Autowired
    private RecipeRepository reciperepository;
    @Autowired
    private RecipeCommentRepository recipecommentrepository;


    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute Comment comment, @RequestParam("postId") int postId, @RequestParam("content") String content, HttpSession session, RedirectAttributes redirectAttributes) {
        // 현재 로그인한 사용자 정보 가져오기 (세션 활용)
        String loggedInNickname = (String) session.getAttribute("loggedInNickname");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        
        if (loggedInNickname != null) {
        // 사용자 정보를 이용하여 작성자 정보 설정
        comment.setNickname(loggedInNickname);
        comment.setUser_id(loggedInUserId);

        // 댓글을 작성한 날짜와 시간을 현재 시간으로 설정
        LocalDateTime now = LocalDateTime.now().withNano(0);
        comment.setCreated_date(now);

        // 게시글 ID를 설정
        comment.setPostId(postId);
        
        comment.setContent(content);
        
        Board board = boardrepository.findByPostId(postId);
        User user = userrepository.findbynickname(loggedInNickname);
        // 댓글 저장
        commentRepository.save(comment);
        Love love = new Love();
        love.setBoard(board);
        love.setUser(user);
        love.setActivity("댓글");
        loverepository.save(love);
        

        return "redirect:/board/" + postId; // 글쓰기 성공 후 게시판 목록 페이지로 리다이렉트
        
        }else {
        	redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!");
			 return "redirect:/login";
        }
    }
    
    @PostMapping("/addRecipeComment")
    public String addRecipeComment(@ModelAttribute RecipeComment recipecomment, @RequestParam("recipe_id") int recipe_id, @RequestParam("content") String content, HttpSession session, RedirectAttributes redirectAttributes) {
        // 현재 로그인한 사용자 정보 가져오기 (세션 활용)
        String loggedInNickname = (String) session.getAttribute("loggedInNickname");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");
        
        User user = userrepository.findbynickname(loggedInNickname);
        Recipe recipe = reciperepository.findById(recipe_id);
        
        if (loggedInNickname != null) {
        // 사용자 정보를 이용하여 작성자 정보 설정
        recipecomment.setUser(user);
        

        // 댓글을 작성한 날짜와 시간을 현재 시간으로 설정
        LocalDateTime now = LocalDateTime.now().withNano(0);
        recipecomment.setCreated_date(now);

        // 게시글 ID를 설정
        recipecomment.setRecipe(recipe);
        
        recipecomment.setContent(content);
        
        
        
        // 댓글 저장
        recipecommentrepository.save(recipecomment);
        Love love = new Love();
        love.setRecipe(recipe);
        love.setUser(user);
        love.setActivity("댓글");
        loverepository.save(love);
        

        return "redirect:/recipe/" + recipe_id; // 글쓰기 성공 후 게시판 목록 페이지로 리다이렉트
        
        }else {
        	redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!");
			 return "redirect:/login";
        }
    }
    
    @GetMapping("/deletecomment/{commentId}/{postId}")
    @Transactional // 트랜잭션 설정
    public String deleteComment(@PathVariable int commentId, @PathVariable int postId, RedirectAttributes redirectAttributes) {
      
        commentRepository.deleteByCommentId(commentId);
        //loverepository.deleteByPostId(postId);
        redirectAttributes.addFlashAttribute("commentdeleteMessage", "댓글 삭제가 완료되었습니다!");

        return "redirect:/board/" + postId; // 댓글 삭제 후 해당 게시물 페이지로 리다이렉트
    }
    
    @GetMapping("/deleterecipecomment/{commentId}/{recipe_id}")
    @Transactional // 트랜잭션 설정
    public String deleterecipeComment(@PathVariable int commentId, @PathVariable int recipe_id, RedirectAttributes redirectAttributes) {
      
        recipecommentrepository.deleteByCommentId(commentId);
        redirectAttributes.addFlashAttribute("commentdeleteMessage", "댓글 삭제가 완료되었습니다!");

        return "redirect:/recipe/" + recipe_id; // 댓글 삭제 후 해당 게시물 페이지로 리다이렉트
    }
	
   
}

