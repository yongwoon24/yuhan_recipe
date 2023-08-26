package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	@Query("SELECT r.title FROM Recipe r WHERE r.recipe_id = :recipeId")
    String findTitleByRecipeId(Long recipeId);
	@Query("SELECT r.main_photo FROM Recipe r WHERE r.recipe_id = :recipeId")
    String findMainPhotoByRecipeId(Long recipeId);
	List<Recipe> findByCategoryNameOrderByTotalLoveDesc(String categoryName);
	List<Recipe> findTop10ByOrderByTotalLoveDesc();
	List<Recipe> findByOrderByTotalLoveDesc();
}
	