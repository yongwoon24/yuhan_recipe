package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;

import jakarta.servlet.http.HttpSession;

public class CommentController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute Comment comment, @RequestParam("postId") int postId, @RequestParam("content") String content, HttpSession session) {
    	System.out.println(postId);
        // 현재 로그인한 사용자 정보 가져오기 (세션 활용)
        String loggedInNickname = (String) session.getAttribute("loggedInNickname");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        // 사용자 정보를 이용하여 작성자 정보 설정
        comment.setNickname(loggedInNickname);
        comment.setUser_id(loggedInUserId);

        // 댓글을 작성한 날짜와 시간을 현재 시간으로 설정
        LocalDateTime now = LocalDateTime.now().withNano(0);
        comment.setCreated_date(now);

        // 게시글 ID를 설정
        comment.setPost_id(postId);
        
        comment.setContent(content);

        // 댓글 저장
        commentRepository.save(comment);
        
        

        return "redirect:/board" + postId; // 글쓰기 성공 후 게시판 목록 페이지로 리다이렉트
    }
}

