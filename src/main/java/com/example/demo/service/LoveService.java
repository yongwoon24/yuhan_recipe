package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;

@Service
public class LoveService {

	@Autowired
	private RecipeRepository reciperepository;
	
	@Autowired
	private LoveRepository loverepository;
	
	@Autowired
	private UserRepository userrepository;
	/*
	public void toggleLike(int recipe_id, String user_id) {
        Recipe recipe = reciperepository.findById(recipe_id);
        User user = userrepository.findByUserId(user_id);

       Optional<Love> existingLike = loverepository.findByRecipeAndUser(recipe, user);

        if (existingLike.isPresent()) {
            // 이미 좋아요를 누른 경우 좋아요 취소
            loverepository.delete(existingLike.get());
        } else {
            // 좋아요 추가
            Love newLove = new Love();
            newLove.setRecipe(recipe);
            newLove.setUserId(user_id);
            loverepository.save(newLove);
        }
    }*/
	
	public void saveLove(Love love) {
		loverepository.save(love);
		updateTotalLikes(love.getRecipe().getRecipe_id());
		updatePeriodLikes(love.getRecipe().getRecipe_id(), LocalDateTime.now());
		updateUserTotalLikes(love.getRecipe().getNickname());
		
		//updateAllPeriodLikes(love.getDate());
	}
	public void saveLove2(Love love) {
		Love love1 = love;
		loverepository.delete(love);
		updateTotalLikes(love1.getRecipe().getRecipe_id());
		updatePeriodLikes(love1.getRecipe().getRecipe_id(), LocalDateTime.now());
		updateUserTotalLikes(love1.getRecipe().getNickname());
		
		//updateAllPeriodLikes(love.getDate());
	}
	
	private void updateTotalLikes(int recipeId) {
        int totalLikes = loverepository.countLovesByRecipeId(recipeId);
        reciperepository.updateTotalLoves(recipeId, totalLikes);
    }
	public void updateUserTotalLikes(String nickname) {
		int totalLikes = 0;
		List<Recipe> recipes = reciperepository.findByNickname(nickname);
		for (Recipe recipe : recipes) {
			int recipeLikes = recipe.getTotalLove();
			totalLikes += recipeLikes;
		}
		User user = userrepository.findbynickname(nickname);
		
		if(user != null) {
			user.setUsertotallikes(totalLikes);
			userrepository.save(user);
		}
	}
	
	//모든 레시피의 모든 좋아요 항목 업데이트
	private void updateAllPeriodLikes(LocalDateTime date) {
        List<Recipe> recipes = reciperepository.findAll();

        for (Recipe recipe : recipes) {
            int recipeId = recipe.getRecipe_id();
            updatePeriodLikes(recipeId, date);
        }
    }
	
	private void updatePeriodLikes(int recipeId, LocalDateTime date) {
        int dailyLikes = loverepository.countDailyLikesByRecipeIdAndDate(recipeId, date.toLocalDate());

        LocalDateTime weekStart = date.minusDays(date.getDayOfWeek().getValue() - 1);
        LocalDateTime weekEnd = weekStart.plusDays(6);
        int weeklyLikes = loverepository.countWeeklyLikesByRecipeIdAndDateRange(recipeId, weekStart, weekEnd);

        int monthlyLikes = loverepository.countMonthlyLikesByRecipeIdAndMonth(recipeId, date);

        reciperepository.updatePeriodLikes(recipeId, dailyLikes, weeklyLikes, monthlyLikes);
    }
}
