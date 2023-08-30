package com.example.demo.service;

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
	}
	
	private void updateTotalLikes(int recipeId) {
        int totalLikes = loverepository.countLovesByRecipeId(recipeId);
        reciperepository.updateTotalLoves(recipeId, totalLikes);
    }

}
