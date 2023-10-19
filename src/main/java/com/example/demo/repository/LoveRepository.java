package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;

public interface LoveRepository extends JpaRepository<Love, Long>{
    List<Love> findByOrderByActivityId();
    
    List<Love> findByRecipe(Recipe recipe);
    
    
    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND l.activity = '좋아요'")
    int countLovesByRecipeId(@Param("recipe_id") int recipe_id);
    
    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND l.activity = '좋아요' AND l.user = :user")
    int countLovesByRecipeId1(@Param("recipe_id") int recipe_id, User user);
    
    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND l.date = :date")
    int countDailyLikesByRecipeIdAndDate(@Param("recipe_id") int recipe_id, @Param("date") LocalDate date);

    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND l.date BETWEEN :startOfWeek AND :endOfWeek")
    int countWeeklyLikesByRecipeIdAndDateRange(@Param("recipe_id") int recipe_id, @Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);

    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND YEAR(l.date) = YEAR(:date) AND MONTH(l.date) = MONTH(:date)")
    int countMonthlyLikesByRecipeIdAndMonth(@Param("recipe_id") int recipe_id, @Param("date") LocalDate date);

    	//int countLovesByRecipe_id(int recipeId);
    
    //Optional<Love> findByRecipeAndUser(Recipe recipe, User user);
    
    void deleteByUser(User user);
    
}