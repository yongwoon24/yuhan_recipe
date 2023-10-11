package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long>{
	
	@Query("SELECT COUNT(l) FROM Scrap l WHERE l.recipe.recipe_id = :recipe_id AND l.user.user_id = :user_id")
    int countscrapByRecipeId(@Param("recipe_id") int recipe_id, @Param("user_id") String user_id);
	


}