package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;

public interface LoveRepository extends JpaRepository<Love, Long>{
    List<Love> findByOrderByActivityId();
    
    
	int countLovesByRecipe_id(int recipeId);
    
    //Optional<Love> findByRecipeAndUser(Recipe recipe, User user);
    
}