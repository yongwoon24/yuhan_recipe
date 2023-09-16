package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	@Query(value = "SELECT * FROM comment WHERE post_id = :postId", nativeQuery = true)
    List<Comment> findByPostId(@Param("postId") int postId);
	@Query(value = "SELECT * FROM comment WHERE comment_id = :commentId", nativeQuery = true)
    List<Comment> findByCommentId(@Param("commentId") int commentId);
	
	void deleteByPostId(int postId);
	void deleteByCommentId(int commentId);
}
