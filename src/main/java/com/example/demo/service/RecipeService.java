package com.example.demo.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
	
//	private RecipeRepository reciperepository;
//	private RecipeImgService recipeImgservice;
//	private RecipeimgRepository recipeImgrepository;
//	
//	//레시피등록
//	public int saveRecipe(RecipeFormDto recipeFormDto, List<MultipartFile> fileList) throws Exception {
//
//        // 상품 등록 (1번)
//        Recipe item = recipeFormDto.createRecipe();
//        reciperepository.save(item);
//
//        // 이미지 등록(2번, 순서중요)
//        for (int i = 0; i < fileList.size(); i++) {
//            Recipeimg recipeimg = new Recipeimg();
//            recipeimg.setRecipe(item);
//            if (i == 0) {
//                recipeimg.setRepimgyn("Y");
//            } else{
//                recipeimg.setRepimgyn("N");
//            }
//            recipeImgservice.saveRecipeImg(recipeimg, fileList.get(i));
//        }
//        return item.getRecipe_id();
//
//    }
	@Autowired
	private RecipeRepository reciperepository;
	
	public void write(Recipe recipe, MultipartFile file) throws Exception{
		String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
 		File savefile = new File(proijectpath, fileName);
		file.transferTo(savefile);
		recipe.setMain_photo(fileName);
		recipe.setMain_photo_path("/img/"+fileName);
		//reciperepository.save(recipe);
	}
	
	public List<Recipe> getAllRecipes() {
        return reciperepository.findAll();
    }

    public Recipe getRecipeById(int id) {
        Optional<Recipe> optionalRecipe = Optional.ofNullable(reciperepository.findById(id));
        return optionalRecipe.orElse(null);
    }
}
