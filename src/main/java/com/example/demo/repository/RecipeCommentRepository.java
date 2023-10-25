package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.RecipeComment;

public interface RecipeCommentRepository extends JpaRepository<RecipeComment, Integer>{

	
    List<RecipeComment> findByRecipe(Recipe recipe);
    void deleteByCommentId(int commentId);
}
