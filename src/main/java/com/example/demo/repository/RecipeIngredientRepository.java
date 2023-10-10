package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Recipe;
import com.example.demo.entity.Recipe_Ingredient;

public interface RecipeIngredientRepository extends JpaRepository<Recipe_Ingredient, Long>{
	List<Recipe_Ingredient> findByRecipe(Recipe recipe);
}
