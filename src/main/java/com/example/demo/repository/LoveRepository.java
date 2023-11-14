package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Board;
import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;

import jakarta.transaction.Transactional;

public interface LoveRepository extends JpaRepository<Love, Long>{
    //List<Love> findByOrderByActivityId();
    Love findByActivityId(Long activityId);
    
    List<Love> findByRecipe(Recipe recipe);
    List<Love> findByRecipeIn(List<Recipe> recipe);
    List<Love> findByBoardInOrRecipeIn(List<Board> board, List<Recipe> recipe);
    //List<Love> findByBoardInAndActivityNotInOrRecipeInAndActivityNotIn(List<Board> board, String acttivity, List<Recipe> recipe, String acttivity1);
    @Query("SELECT l FROM Love l WHERE (l.activity <> '조회') AND (l.board IN :boards OR l.recipe IN :recipes) ORDER BY l.date DESC")
    List<Love> findLovesByActivityNotEqualAndBoardInOrRecipeInOrderByDateAtDesc(@Param("boards") List<Board> boards, @Param("recipes") List<Recipe> recipes);
    
    @Query("SELECT l FROM Love l WHERE l.activity <> '조회' AND (l.board IN :boards OR l.recipe IN :recipes) AND l.user.user_id <> :username AND l.Token = false ORDER BY l.date DESC")
    List<Love> findLovesByActivityNotEqualAndBoardInOrRecipeInOrderByDateAtDesc1(
            @Param("boards") List<Board> boards, 
            @Param("recipes") List<Recipe> recipes, 
            @Param("username") String username
    );
    
    
    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND l.activity = '좋아요'")
    int countLovesByRecipeId(@Param("recipe_id") int recipe_id);
    
    @Query("SELECT l FROM Love l WHERE l.user = :user AND l.activity = '스크랩' AND l.recipe = :recipe")
    Love findByUserRecipeScrap(@Param("user") User user, @Param("recipe") Recipe recipe);
    
    @Query("SELECT l FROM Love l WHERE l.user = :user AND l.activity = '좋아요' AND l.recipe = :recipe")
    Love findByUserRecipeLike(@Param("user") User user, @Param("recipe") Recipe recipe);
    
    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND l.activity = '좋아요' AND l.user = :user")
    int countLovesByRecipeId1(@Param("recipe_id") int recipe_id, User user);
    
//    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND l.date = :date AND l.activity = '좋아요'")
//    int countDailyLikesByRecipeIdAndDate(@Param("recipe_id") int recipe_id, @Param("date") LocalDateTime date);

    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND DATE(l.date) = :dateTruncated AND l.activity = '좋아요'")
    int countDailyLikesByRecipeIdAndDate(@Param("recipe_id") int recipe_id, @Param("dateTruncated") LocalDate dateTruncated);
    
    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND l.date BETWEEN :startOfWeek AND :endOfWeek AND l.activity = '좋아요'")
    int countWeeklyLikesByRecipeIdAndDateRange(@Param("recipe_id") int recipe_id, @Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("SELECT COUNT(l) FROM Love l WHERE l.recipe.recipe_id = :recipe_id AND YEAR(l.date) = YEAR(:date) AND MONTH(l.date) = MONTH(:date) AND l.activity = '좋아요'")
    int countMonthlyLikesByRecipeIdAndMonth(@Param("recipe_id") int recipe_id, @Param("date") LocalDateTime date);

       //int countLovesByRecipe_id(int recipeId);
    
    //Optional<Love> findByRecipeAndUser(Recipe recipe, User user);
    
    void deleteByUser(User user);
    
    @Query("SELECT ua.recipe FROM Love ua WHERE ua.user = :user AND ua.activity = '조회' ORDER BY ua.date DESC")
    List<Recipe> findUserActivitiesWithdesc(User user);
    void deleteByActivityId(int id);
    
    @Query("SELECT l FROM Love l WHERE l.activity = '조회' AND l.user.user_id = :username GROUP BY l.recipe ORDER BY MAX(l.date) DESC LIMIT 10")
    List<Love> findDistinctTop10ByActivityOrderByDateDesc(@Param("username") String username);
    
    @Query(value = "DELETE * FROM Love WHERE post_id = :postId", nativeQuery = true)
    void deleteByPostId(int postId);
    
    
}