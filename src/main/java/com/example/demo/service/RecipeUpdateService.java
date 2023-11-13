package com.example.demo.service;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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
  //@Scheduled(fixedRate = 5000) // 5초
    public void updateToday() {
        Random random = new Random();
        int max = recipeRepository.maxRecipeId();
        int min = recipeRepository.minRecipeId();

        int usermax = todayrepository.maxTodayId();

        for (int i = 1; i <= usermax; i++) {
            Today today = todayrepository.findById(i);
            Set<Integer> uniqueNums = new HashSet<>();

            while (uniqueNums.size() < 10) {
                int randomnum;
                Recipe recipe;

                do {
                    randomnum = random.nextInt(max - min + 1) + min;
                    recipe = recipeRepository.findById(randomnum);
                } while (uniqueNums.contains(randomnum) || recipe == null);

                uniqueNums.add(randomnum);
            }

            if (today != null) {
                Integer[] uniqueNumsArray = uniqueNums.toArray(new Integer[0]);

                today.setNo1(uniqueNumsArray[0]);
                today.setNo2(uniqueNumsArray[1]);
                today.setNo3(uniqueNumsArray[2]);
                today.setNo4(uniqueNumsArray[3]);
                today.setNo5(uniqueNumsArray[4]);
                today.setNo6(uniqueNumsArray[5]);
                today.setNo7(uniqueNumsArray[6]);
                today.setNo8(uniqueNumsArray[7]);
                today.setNo9(uniqueNumsArray[8]);
                today.setNo10(uniqueNumsArray[9]);

                todayrepository.save(today);
            }
        }
    }

   
    
    public void updateToday2(Today today) {

        Random random = new Random();
        int max = recipeRepository.maxRecipeId();
        int min = recipeRepository.minRecipeId();





            int[] nums = new int[10];
            Set<Integer> uniqueNums = new HashSet<>();

            while (uniqueNums.size() < 10) {
                int randomnum;
                Recipe recipe;

                do {
                    randomnum = random.nextInt(max - min + 1) + min;
                    recipe = recipeRepository.findById(randomnum);
                } while (uniqueNums.contains(randomnum) || recipe == null);

                uniqueNums.add(randomnum);


            }

            Integer[] uniqueNumsArray = uniqueNums.toArray(new Integer[0]);
            today.setNo1(uniqueNumsArray[0]);
            today.setNo2(uniqueNumsArray[1]);
            today.setNo3(uniqueNumsArray[2]);
            today.setNo4(uniqueNumsArray[3]);
            today.setNo5(uniqueNumsArray[4]);
            today.setNo6(uniqueNumsArray[5]);
            today.setNo7(uniqueNumsArray[6]);
            today.setNo8(uniqueNumsArray[7]);
            today.setNo9(uniqueNumsArray[8]);
            today.setNo10(uniqueNumsArray[9]);
                //todayrepository.save(today);


    }

public void updateToday1() {
    Random random = new Random();
    int max = recipeRepository.maxRecipeId();
    int min = recipeRepository.minRecipeId();

    int usermax = todayrepository.maxTodayId();

    for (int i = 1; i <= usermax; i++) {
        Today today = todayrepository.findById(i);
        Set<Integer> uniqueNums = new HashSet<>();

        while (uniqueNums.size() < 10) {
            int randomnum;
            Recipe recipe;

            do {
                randomnum = random.nextInt(max - min + 1) + min;
                recipe = recipeRepository.findById(randomnum);
            } while (uniqueNums.contains(randomnum) || recipe == null);

            uniqueNums.add(randomnum);
        }

        if (today != null) {
            Integer[] uniqueNumsArray = uniqueNums.toArray(new Integer[0]);

            today.setNo1(uniqueNumsArray[0]);
            today.setNo2(uniqueNumsArray[1]);
            today.setNo3(uniqueNumsArray[2]);
            today.setNo4(uniqueNumsArray[3]);
            today.setNo5(uniqueNumsArray[4]);
            today.setNo6(uniqueNumsArray[5]);
            today.setNo7(uniqueNumsArray[6]);
            today.setNo8(uniqueNumsArray[7]);
            today.setNo9(uniqueNumsArray[8]);
            today.setNo10(uniqueNumsArray[9]);

            todayrepository.save(today);
        }
    }
}}