package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Recipe;
import com.example.demo.entity.Step;

public interface StepRepository extends JpaRepository<Step, Long>{
	//@Query("SELECT s FROM Step s WHERE s.recipe_id = :recipe_id")
	List<Step> findByRecipe(Recipe recipe);
	
}
