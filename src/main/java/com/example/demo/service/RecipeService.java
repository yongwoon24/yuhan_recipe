package com.example.demo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Recipe_Ingredient;
import com.example.demo.entity.Step;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

	@Autowired
	private RecipeRepository reciperepository;
	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;

	@Autowired
	private IngredientRepository ingredientRepository;
	
	 public List<Recipe> findRecipesByIngredients(List<String> ingredientNames) {
	        return reciperepository.findByRecipeIngredientsIngredientIngredientNameIn(ingredientNames);
	    }

	public void createRecipe(Recipe recipe, List<String> ingredientNames, List<String> mensurations) {
		// Recipe newRecipe = new Recipe();

		List<Recipe_Ingredient> recipeIngredients = new ArrayList<>();
		for (int i = 0; i < ingredientNames.size(); i++) {
			String ingredientName = ingredientNames.get(i);
			String mensuration = mensurations.get(i);

			Ingredient existingIngredient = ingredientRepository.findByIngredientName(ingredientName);
			if (existingIngredient != null) {
				// 이미 존재하는 재료 정보인 경우, 기존 재료 정보를 사용
				Recipe_Ingredient recipeIngredient = new Recipe_Ingredient();
				recipeIngredient.setRecipe(recipe);
				recipeIngredient.setIngredient(existingIngredient);
				recipeIngredient.setMensuration(mensuration);
				recipeIngredients.add(recipeIngredient);
			} else {
				// 존재하지 않는 재료 정보인 경우, 새로운 재료 정보를 생성하여 저장
				Ingredient newIngredient = new Ingredient();
				newIngredient.setIngredient_name(ingredientName);
				ingredientRepository.save(newIngredient);

				Recipe_Ingredient recipeIngredient = new Recipe_Ingredient();
				recipeIngredient.setRecipe(recipe);
				recipeIngredient.setIngredient(newIngredient);
				recipeIngredient.setMensuration(mensuration);
				recipeIngredients.add(recipeIngredient);
			}
		}

		recipe.setRecipeIngredients(recipeIngredients);

	}

	public void createStep(Recipe recipe, List<String> SContents, List<String> Singtxts, List<String> Stooltxts,
			List<String> Stips, List<String> Scontroltxts) {

		List<Step> steps = new ArrayList<>();
		for (int i = 0; i < SContents.size(); i++) {
			String SContent = SContents.get(i);
			String Singtxt = Singtxts.get(i);
			String Stooltxt = Stooltxts.get(i);
			String Stip = Stips.get(i);
			String Scontroltxt = Scontroltxts.get(i);

			Step step = new Step();
			step.setRecipe(recipe);
			step.setSContent(SContent);
			step.setScontroltxt(Scontroltxt);
			step.setSingtxt(Singtxt);
			step.setStip(Stip);
			step.setStooltxt(Stooltxt);
			steps.add(step);
		}

		recipe.setSteps(steps);
		;

	}

	public void write(Recipe recipe, MultipartFile file) throws Exception {
		String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		File savefile = new File(proijectpath, fileName);
		file.transferTo(savefile);
		recipe.setMain_photo(fileName);
		recipe.setMain_photo_path("/img/" + fileName);
		// reciperepository.save(recipe);
	}

	public List<Recipe> getAllRecipes() {
		return reciperepository.findAll();
	}

	public Recipe getRecipeById(int id) {
		Optional<Recipe> optionalRecipe = Optional.ofNullable(reciperepository.findById(id));
		return optionalRecipe.orElse(null);
	}

	@Transactional
	public void deletePostWithImage(int recipe_id) {
		// 1. 게시물 정보 조회
		Recipe recipe = reciperepository.findById(recipe_id);

		if (recipe != null) {
			// 2. 이미지 파일 삭제
			String imagePath = recipe.getMain_photo_path();
			if (imagePath != null) {
				deleteImage(imagePath);
			}
		}

		// 3. 게시물 및 이미지 정보 삭제
		reciperepository.delete(recipe);
	}

	private void deleteImage(String imagePath) {
		File imageFile = new File(imagePath);
		if (imageFile.exists()) {
			imageFile.delete();
		}
	}
	
	 public Page<Recipe> getRecipesByPage(int pageNumber, int pageSize) {
	        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize); // 페이지 번호는 0부터 시작하므로 -1
	        return reciperepository.findAll(pageRequest);
	    }
	 
	 

}
