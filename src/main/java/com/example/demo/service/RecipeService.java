package com.example.demo.service;

import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

@Service
public class RecipeService {

	@Autowired
	RecipeRepository recipeRepository;
	
	public void save(Recipe files) {
		Recipe f = new Recipe();
		f.setMain_photo(files.getMain_photo());
		f.setMain_photo_oriname(files.getMain_photo_oriname());
		f.setMain_photo_url(files.getMain_photo_url());
		
		recipeRepository.save(f);
	}
}
