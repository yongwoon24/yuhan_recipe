package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	@Query("SELECT r.title FROM Recipe r WHERE r.recipe_id = :recipeId")
	String findTitleByRecipeId(Long recipeId);

	@Query("SELECT r.main_photo FROM Recipe r WHERE r.recipe_id = :recipeId")
	String findMainPhotoByRecipeId(Long recipeId);

	List<Recipe> findByCategoryNameOrderByTotalLoveDesc(String categoryName);
	List<Recipe> findByRecipeVerifiedAndCategoryNameOrderByTotalLoveDesc(boolean recipeVerified, String categoryName);

	List<Recipe> findByCategoryNameOrderByDailyLoveDesc(String categoryName);
	List<Recipe> findByRecipeVerifiedAndCategoryNameOrderByDailyLoveDesc(boolean recipeVerified, String categoryName);

	List<Recipe> findByCategoryNameOrderByWeeklyLoveDesc(String categoryName);
	List<Recipe> findByRecipeVerifiedAndCategoryNameOrderByWeeklyLoveDesc(boolean recipeVerified, String categoryName);

	List<Recipe> findByCategoryNameOrderByMonthlyLoveDesc(String categoryName);
	List<Recipe> findByRecipeVerifiedAndCategoryNameOrderByMonthlyLoveDesc(boolean recipeVerified, String categoryName);

	List<Recipe> findTop10ByOrderByTotalLoveDesc();
	
	List<Recipe> findTop10ByRecipeVerifiedOrderByTotalLoveDesc(boolean recipeVerified);

	List<Recipe> findByOrderByTotalLoveDesc();
	
	List<Recipe> findByRecipeVerifiedOrderByTotalLoveDesc(boolean recipeVerified);

	Recipe findById(int recipe_id);
	

	
	//List<Recipe> findByTitle(Stiring title);

	@Transactional
	@Modifying
	@Query("UPDATE Recipe r SET r.viewcount = r.viewcount + 1 WHERE r.recipe_id = :recipe_id")
	void incrementViewCount(@Param("recipe_id") int recipe_id);

	@Transactional
	@Modifying
	@Query("UPDATE Recipe r " + "SET r.totalLove = :totalLove " + "WHERE r.recipe_id = :recipe_id "
			+ "AND EXISTS (SELECT ua " + "FROM Love ua " + "WHERE ua.activity = '좋아요')")

	void updateTotalLoves(@Param("recipe_id") int recipe_id, @Param("totalLove") int totalLove);

	@Transactional
	@Modifying
	@Query("UPDATE Recipe r SET r.totalLove = (SELECT COUNT(u.activityId) FROM Love u)")
	void updateTotalFromActivity();

	@Transactional
	@Modifying
	@Query("UPDATE Recipe r SET r.dailyLove = :dailyLove, r.weeklyLove = :weeklyLove, r.monthlyLove = :monthlyLove WHERE r.recipe_id = :recipe_id")
	void updatePeriodLikes(@Param("recipe_id") int recipe_id, @Param("dailyLove") int dailyLove,
			@Param("weeklyLove") int weeklyLove, @Param("monthlyLove") int monthlyLove);

	List<Recipe> findByOrderByDailyLoveDesc();
	
	List<Recipe> findByRecipeVerifiedOrderByDailyLoveDesc(boolean recipeVerified);

	List<Recipe> findByOrderByWeeklyLoveDesc();
	
	List<Recipe> findByRecipeVerifiedOrderByWeeklyLoveDesc(boolean recipeVerified);

	List<Recipe> findByOrderByMonthlyLoveDesc();
	
	List<Recipe> findByRecipeVerifiedOrderByMonthlyLoveDesc(boolean recipeVerified);

	List<Recipe> findByCategoryNameIn(List<String> categori);

	List<Recipe> findByNicknameOrderByCreateddateDesc(String nickname);
	
	List<Recipe> findByRecipeVerifiedAndNicknameOrderByCreateddateDesc(boolean recipeVerified, String nickname);

	List<Recipe> findByRecipeIngredientsIngredientIngredientNameIn(List<String> ingredientNames);

	List<Recipe> findByRecipeIngredientsIngredientIngredientNameInOrderByCreateddateDesc(List<String> ingredientNames);
	List<Recipe> findByRecipeVerifiedAndRecipeIngredientsIngredientIngredientNameInOrderByCreateddateDesc(boolean recipeVerified, List<String> ingredientNames);

	List<Recipe> findByRecipeIngredientsIngredientIngredientNameInOrderByViewcountDesc(List<String> ingredientNames);
	
	List<Recipe> findByRecipeVerifiedAndRecipeIngredientsIngredientIngredientNameInOrderByViewcountDesc(boolean recipeVerified, List<String> ingredientNames);

	List<Recipe> findByCategoryNameInAndRecipeIngredientsIngredientIngredientNameIn(List<String> categName,
			List<String> ingredientName);

	List<Recipe> findByCategoryNameInAndRecipeIngredientsIngredientIngredientNameInOrderByCreateddateDesc(
			List<String> categName, List<String> ingredientName);
	
	List<Recipe> findByRecipeVerifiedAndCategoryNameInAndRecipeIngredientsIngredientIngredientNameInOrderByCreateddateDesc(
			boolean recipeVerified, List<String> categName, List<String> ingredientName);

	List<Recipe> findByCategoryNameInAndRecipeIngredientsIngredientIngredientNameInOrderByViewcountDesc(
			List<String> categName, List<String> ingredientName);
	
	List<Recipe> findByRecipeVerifiedAndCategoryNameInAndRecipeIngredientsIngredientIngredientNameInOrderByViewcountDesc(
			boolean recipeVerified, List<String> categName, List<String> ingredientName);

	List<Recipe> findAllByOrderByCreateddateDesc();
	
	List<Recipe> findByRecipeVerifiedOrderByCreateddateDesc(boolean recipeVerified);

	List<Recipe> findAllByOrderByViewcountDesc();
	
	List<Recipe> findByRecipeVerifiedOrderByViewcountDesc(boolean recipeVerified);

	List<Recipe> findByCategoryNameInOrderByCreateddateDesc(List<String> categories);
	List<Recipe> findByRecipeVerifiedAndCategoryNameInOrderByCreateddateDesc(boolean recipeVerified, List<String> categories);
	
	List<Recipe> findByCategoryNameInOrderByViewcountDesc(List<String> categories);
	
	List<Recipe> findByRecipeVerifiedAndCategoryNameInOrderByViewcountDesc(boolean recipeVerified, List<String> categories);
	List<Recipe> findByNickname(String nickname);
	
	@Transactional
	@Modifying
	@Query("SELECT l.recipe FROM Love l WHERE l.activity = '좋아요' AND l.user.user_id = :userId")
    List<Recipe> findRecipesLikedByUser(String userId);
	
	@Transactional
	@Modifying
	@Query("SELECT l.recipe FROM Scrap l WHERE l.user.user_id = :userId")
    List<Recipe> findRecipesScrapByUser(String userId);
	
	List<Recipe> findByTitleContaining(String title, Sort sort);
	List<Recipe> findByTitleContainingOrTagContent(String title, String tagcontent, Sort sort);
	
	List<Recipe> findByTitleContainingAndRecipeVerifiedOrTagContentAndRecipeVerified(String title,boolean recipeVerified, String tagcontent, Sort sort, boolean recipeVerified1);
	
	void deleteByNickname(String nickname);
	
	@Query("SELECT MAX(r.id) FROM Recipe r")
	int maxRecipeId();
	
	@Query("SELECT MIN(r.id) FROM Recipe r")
	int minRecipeId();
	
	
}