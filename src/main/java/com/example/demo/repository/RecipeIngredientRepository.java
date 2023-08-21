package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Recipe_Ingredient;

public interface RecipeIngredientRepository extends JpaRepository<Recipe_Ingredient, Long>{

}
