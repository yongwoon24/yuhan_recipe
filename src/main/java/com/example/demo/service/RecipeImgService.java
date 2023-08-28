//package com.example.demo.service;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.thymeleaf.util.StringUtils;
//
//import com.example.demo.entity.Recipeimg;
//import com.example.demo.repository.RecipeimgRepository;
//
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class RecipeImgService {
//
//	@Value("${recipeImgLocation}")
//	private String recipeImgLocation;
//	
//	private Fileservice fileservice;
//	private RecipeimgRepository recipeimgrepository;
//	
//	//레시피 이미지 저장
//	 public void saveRecipeImg(Recipeimg recipeimg, MultipartFile file) throws IOException {
//
//	        String oriImgName = file.getOriginalFilename();
//	        String imgName = "";
//	        String imgUrl = "";
//
//	        // 파일 업로드
//	        if (!StringUtils.isEmpty(oriImgName)) {
//	            imgName = fileservice.uploadFile(recipeImgLocation, oriImgName, file.getBytes());
//	            imgUrl = "/images/item/" + imgName;
//	        }
//	        
//	        //레시피 이미지 정보 저장
//	        recipeimg.updateRecipeImg(oriImgName, imgName, imgUrl);
//	        recipeimgrepository.save(recipeimg);
//	 	}
//	 
//		//레시피 이미지 수정
//	 public void updateRecipeImg(Long recipeimgId, MultipartFile file) throws IOException {
//		 	
//		 	// 상품 이미지를 수정했다면
//	        if (!file.isEmpty()) {
//	            Recipeimg savedRecipeImg = recipeimgrepository.findById(recipeimgId).orElseThrow(EntityNotFoundException::new);
//
//	            // 기존 이미지 파일이 존재한다면 삭제
//	            if (!StringUtils.isEmpty(savedRecipeImg.getImgName())) {
//	                fileservice.deleteFile(recipeImgLocation + "/" + savedRecipeImg);
//	            }
//		 	
//	        String oriImgName = file.getOriginalFilename();
//	        String imgName = fileservice.uploadFile(recipeImgLocation, oriImgName, file.getBytes());;
//	        String imgUrl = "";
//
//	        savedRecipeImg.updateRecipeImg(oriImgName, imgName, imgUrl);
//	 	}
//	 
//	 
//	 }
//}
//
