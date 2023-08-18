package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Recipe;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	@Query("SELECT r.title FROM Recipe r WHERE r.recipe_id = :recipeId")
    String findTitleByRecipeId(Long recipeId);
}
	