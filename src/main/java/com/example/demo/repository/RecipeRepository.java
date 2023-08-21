package com.example.demo.repository;

import java.nio.file.Files;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Recipe;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	

}
