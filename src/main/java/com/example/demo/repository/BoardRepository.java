package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
   @Query(value = "SELECT * FROM your_table_name WHERE post_id = :postId", nativeQuery = true)
   List<Board> findByPostId(@Param("postId") int postId);

}