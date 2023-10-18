package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Recipe_Ingredient;
import com.example.demo.entity.Scrap;
import com.example.demo.entity.Step;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.ScrapRepository;

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
	private ScrapRepository scraprepository;

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
	
	public void createtag(Recipe recipe, List<String> tag) {
		List<Tag> tags = new ArrayList<>();
		
		for (int i = 0; i < tag.size(); i++) {
			Tag ntag = new Tag();
			ntag.setRecipe(recipe);
			String tagc = tag.get(i);
			//System.out.println(tagc);
			ntag.setContent(tagc);
			tags.add(ntag);
		}
		recipe.setTag(tags);
	}
	
	public void editStep(Recipe recipe, List<String> SContents, List<String> Singtxts, List<String> Stooltxts,
	        List<String> Stips, List<String> Scontroltxts, List<MultipartFile> cookingStepImages, List<String> photo,
	        List<String> photopath) {

	    List<Step> steps = new ArrayList<>();
	    
	    int maxSize = Math.max(Math.max(Math.max(SContents.size(), Singtxts.size()), Stooltxts.size()), Math.max(Stips.size(), Scontroltxts.size()));
	    
	    for (int i = 0; i < maxSize; i++) {
	        String SContent = (i < SContents.size()) ? SContents.get(i) : null;
	        String Singtxt = (i < Singtxts.size()) ? Singtxts.get(i) : null;
	        String Stooltxt = (i < Stooltxts.size()) ? Stooltxts.get(i) : null;
	        String Stip = (i < Stips.size()) ? Stips.get(i) : null;
	        String Scontroltxt = (i < Scontroltxts.size()) ? Scontroltxts.get(i) : null;

	        // 각 리스트에서 요소가 존재하는지 확인
	        if (SContent != null || Singtxt != null || Stooltxt != null || Stip != null || Scontroltxt != null|| (cookingStepImages != null && !cookingStepImages.get(i).isEmpty())) {
	            Step step = new Step();
	            step.setRecipe(recipe);
	            step.setSContent(SContent);
	            step.setScontroltxt(Scontroltxt);
	            step.setSingtxt(Singtxt);
	            step.setStip(Stip);
	            step.setStooltxt(Stooltxt);
	            
	            if ((cookingStepImages != null && !cookingStepImages.get(i).isEmpty())) {
	                try {
	                	String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img"; // 단계 이미지 저장 경로
	                	MultipartFile stepImageFile = cookingStepImages.get(i);
	                   
	                    UUID uuid = UUID.randomUUID();
	            		String fileName = uuid + "_" + stepImageFile.getOriginalFilename();
	            		System.out.println(fileName);
	            		File savefile = new File(proijectpath, fileName);
	            		stepImageFile.transferTo(savefile);
	            		step.setSphoto(fileName);
	            		step.setSphotopath("/img/" + fileName);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    // 이미지 저장 중 오류 발생 시 처리
	                }
	            }else {
	            	step.setSphoto(photo.get(i));
            		step.setSphotopath(photopath.get(i));
	            }
	            steps.add(step);
	        } else {
	            // 처리할 수 없는 상황이라면 예외 처리 또는 오류 처리를 수행하세요.
	            // 예를 들어, 로그를 남기거나 사용자에게 오류 메시지를 표시할 수 있습니다.
	        }
	    }

	    recipe.setSteps(steps);
	}

	public void createStep(Recipe recipe, List<String> SContents, List<String> Singtxts, List<String> Stooltxts,
	        List<String> Stips, List<String> Scontroltxts, List<MultipartFile> cookingStepImages) {

	    List<Step> steps = new ArrayList<>();
	    
	    int maxSize = Math.max(Math.max(Math.max(SContents.size(), Singtxts.size()), Stooltxts.size()), Math.max(Stips.size(), Scontroltxts.size()));
	    
	    for (int i = 0; i < maxSize; i++) {
	        String SContent = (i < SContents.size()) ? SContents.get(i) : null;
	        String Singtxt = (i < Singtxts.size()) ? Singtxts.get(i) : null;
	        String Stooltxt = (i < Stooltxts.size()) ? Stooltxts.get(i) : null;
	        String Stip = (i < Stips.size()) ? Stips.get(i) : null;
	        String Scontroltxt = (i < Scontroltxts.size()) ? Scontroltxts.get(i) : null;

	        // 각 리스트에서 요소가 존재하는지 확인
	        if (SContent != null || Singtxt != null || Stooltxt != null || Stip != null || Scontroltxt != null|| (cookingStepImages != null && !cookingStepImages.get(i).isEmpty())) {
	            Step step = new Step();
	            step.setRecipe(recipe);
	            step.setSContent(SContent);
	            step.setScontroltxt(Scontroltxt);
	            step.setSingtxt(Singtxt);
	            step.setStip(Stip);
	            step.setStooltxt(Stooltxt);
	            
	            if ((cookingStepImages != null && !cookingStepImages.get(i).isEmpty())) {
	                try {
	                	String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img"; // 단계 이미지 저장 경로
	                	MultipartFile stepImageFile = cookingStepImages.get(i);
	                   
	                    UUID uuid = UUID.randomUUID();
	            		String fileName = uuid + "_" + stepImageFile.getOriginalFilename();
	            		System.out.println(fileName);
	            		File savefile = new File(proijectpath, fileName);
	            		stepImageFile.transferTo(savefile);
	            		step.setSphoto(fileName);
	            		step.setSphotopath("/img/" + fileName);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    // 이미지 저장 중 오류 발생 시 처리
	                }
	            }
	            steps.add(step);
	        } else {
	            // 처리할 수 없는 상황이라면 예외 처리 또는 오류 처리를 수행하세요.
	            // 예를 들어, 로그를 남기거나 사용자에게 오류 메시지를 표시할 수 있습니다.
	        }
	    }

	    recipe.setSteps(steps);
	}
	@Value("${upload.path}") // 파일 업로드 경로를 프로퍼티에서 읽어옵니다.
    private String uploadPath;
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
	
	
	public void editwrite(Recipe recipe, MultipartFile file, String Rphoto,String Rphotopath) throws Exception {
		if(file != null && !file.isEmpty()) {
		String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		File savefile = new File(proijectpath, fileName);
		file.transferTo(savefile);
		recipe.setMain_photo(fileName);
		recipe.setMain_photo_path("/img/" + fileName);
		}else {
			recipe.setMain_photo(Rphoto);
			recipe.setMain_photo_path(Rphotopath);
		}
		// reciperepository.save(recipe);
	}
	
	public void userwrite(User user, MultipartFile file) throws Exception {
		String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		File savefile = new File(proijectpath, fileName);
		file.transferTo(savefile);
		user.setUserphotopath("/img/" + fileName);
		
		// reciperepository.save(recipe);
	}
	
	public void stepwrite(Recipe recipe, MultipartFile file) throws Exception {
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

		List<Scrap> scraps = scraprepository.findByRecipe(recipe);
		
		if (recipe != null) {
			// 2. 이미지 파일 삭제
			String imagePath = recipe.getMain_photo_path();
			if (imagePath != null) {
				deleteImage(imagePath);
			}
		}

		// 3. 게시물 및 이미지 정보 삭제
		
		for (int i = 0; i < scraps.size(); i++) {
		
			scraprepository.delete(scraps.get(i));
		}
		
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
