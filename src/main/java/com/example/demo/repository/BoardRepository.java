package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Board;
import com.example.demo.entity.Recipe;

public interface BoardRepository extends JpaRepository<Board, Long>{
   @Query(value = "SELECT * FROM board WHERE post_id = :postId", nativeQuery = true)
    Board findByPostId(@Param("postId") int postId);
   
   List<Board> findByTitleContaining(String title, Sort sort);
   List<Board> findByNicknameContaining(String nickname, Sort sort);
   List<Board> findByNicknameOrderByPostIdDesc(String nickname);
   
	void deleteByPostId(int postId);
	void deleteByNickname(String nickname);

	List<Board> findByNickname(String nickname);
	
	// 관리자 글을 맨 위에 표시하기 위한 메서드
    @Query("SELECT b FROM Board b ORDER BY CASE WHEN b.nickname = '관리자' THEN 0 ELSE 1 END, b.created_date DESC")
    List<Board> findAllOrderedByAdminFirst();
}


