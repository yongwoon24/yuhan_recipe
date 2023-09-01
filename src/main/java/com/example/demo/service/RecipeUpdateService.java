package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class RecipeUpdateService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private LoveRepository loveRepository;

    @Scheduled(cron = "0 * * * * *") // 매일 자정에 실행
    public void updateRecipeStats() {
        LocalDate currentDate = LocalDate.now();
        LocalDate weekStart = currentDate.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate weekEnd = weekStart.plusDays(6);
        LocalDate monthStart = currentDate.with(TemporalAdjusters.firstDayOfMonth());

        // 모든 레시피의 daily, weekly, monthly 좋아요 수 업데이트
        List<Recipe> allRecipes = recipeRepository.findAll();
        for (Recipe recipe : allRecipes) {
            int dailyLikes = loveRepository.countDailyLikesByRecipeIdAndDate(recipe.getRecipe_id(), currentDate);
            int weeklyLikes = loveRepository.countWeeklyLikesByRecipeIdAndDateRange(recipe.getRecipe_id(), weekStart, weekEnd);
            int monthlyLikes = loveRepository.countMonthlyLikesByRecipeIdAndMonth(recipe.getRecipe_id(), currentDate);

            recipe.setDailyLove(dailyLikes);
            recipe.setWeeklyLove(weeklyLikes);
            recipe.setMonthlyLove(monthlyLikes);

            recipeRepository.save(recipe);
        }
    }
}
