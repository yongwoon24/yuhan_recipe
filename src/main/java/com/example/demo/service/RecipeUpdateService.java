package com.example.demo.service;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Recipe;
import com.example.demo.entity.Today;
import com.example.demo.entity.User;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.TodayRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class RecipeUpdateService {

    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoveRepository loveRepository;
    @Autowired
    private TodayRepository todayrepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void updateRecipeStats() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime weekStart = currentDate.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDateTime weekEnd = weekStart.plusDays(6);
        LocalDateTime monthStart = currentDate.with(TemporalAdjusters.firstDayOfMonth());

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
    
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    //@Scheduled(fixedRate = 10000) // 10초
    public void updateToday() {
        Random random = new Random();
        int max = recipeRepository.maxRecipeId();
        int min = recipeRepository.minRecipeId();
        
        int usermax = todayrepository.maxTodayId();

        for (int i = 1; i <= usermax; i++) {
            Today today = todayrepository.findById(i);
            int[] nums = new int[10];
            Set<Integer> uniqueNums = new HashSet<>();

            for (int j = 0; j < 10; j++) {
                int randomnum;
                Recipe recipe;
                
                do {
                    randomnum = random.nextInt(max - min + 1) + min;
                } while (uniqueNums.contains(randomnum) && (recipe = recipeRepository.findById(randomnum)) == null);
                
                uniqueNums.add(randomnum);
                nums[j] = randomnum;
            }

            if (today != null) {
                today.setNo1(nums[0]);
                today.setNo2(nums[1]);
                today.setNo3(nums[2]);
                today.setNo4(nums[3]);
                today.setNo5(nums[4]);
                today.setNo6(nums[5]);
                today.setNo7(nums[6]);
                today.setNo8(nums[7]);
                today.setNo9(nums[8]);
                today.setNo10(nums[9]);
                todayrepository.save(today);
            }
        }
    }
    
    public void updateToday1(HttpSession session) {
    	String loggedInUserId = (String) session.getAttribute("loggedInUserId");
    	User user = userRepository.findByUser_id1(loggedInUserId);
        Random random = new Random();
        int max = recipeRepository.maxRecipeId();
        int min = recipeRepository.minRecipeId();
        
        

        
            Today today = todayrepository.findByUser(user);
            int[] nums = new int[10];
            Set<Integer> uniqueNums = new HashSet<>();

            for (int j = 0; j < 10; j++) {
                int randomnum;
                Recipe recipe;
                
                do {
                    randomnum = random.nextInt(max - min + 1) + min;
                } while (uniqueNums.contains(randomnum) && (recipe = recipeRepository.findById(randomnum)) == null);
                
                uniqueNums.add(randomnum);
                nums[j] = randomnum;
            }

            if (today != null) {
                today.setNo1(nums[0]);
                today.setNo2(nums[1]);
                today.setNo3(nums[2]);
                today.setNo4(nums[3]);
                today.setNo5(nums[4]);
                today.setNo6(nums[5]);
                today.setNo7(nums[6]);
                today.setNo8(nums[7]);
                today.setNo9(nums[8]);
                today.setNo10(nums[9]);
                todayrepository.save(today);
            }
        
    }
    
    public void updateToday2(Today today) {
    	
        Random random = new Random();
        int max = recipeRepository.maxRecipeId();
        int min = recipeRepository.minRecipeId();
        
        

        
            
            int[] nums = new int[10];
            Set<Integer> uniqueNums = new HashSet<>();

            for (int j = 0; j < 10; j++) {
                int randomnum;
                Recipe recipe;
                
                do {
                    randomnum = random.nextInt(max - min + 1) + min;
                } while (uniqueNums.contains(randomnum) && (recipe = recipeRepository.findById(randomnum)) == null);
                
                uniqueNums.add(randomnum);
                nums[j] = randomnum;
            }

            
                today.setNo1(nums[0]);
                today.setNo2(nums[1]);
                today.setNo3(nums[2]);
                today.setNo4(nums[3]);
                today.setNo5(nums[4]);
                today.setNo6(nums[5]);
                today.setNo7(nums[6]);
                today.setNo8(nums[7]);
                today.setNo9(nums[8]);
                today.setNo10(nums[9]);
                //todayrepository.save(today);
            
        
    }
}
