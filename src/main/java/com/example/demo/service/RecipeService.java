package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
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
}
