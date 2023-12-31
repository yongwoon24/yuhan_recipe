package com.example.demo.formdto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.demo.entity.Recipe;

import jakarta.persistence.Column;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipeFormDto {
	
	private int recipe_id;
	
	@NotBlank(message = "제목은 필수 입력값입니다.")
	private String title;
	
	
	private LocalDate created_date;
	private String user_id;
	private String category_name;
	private Integer view_count;
	private Integer totalLove;
	private String main_photo;
	private String main_photo_path;
	
	public String getMain_photo() {
		return main_photo;
	}

	public void setMain_photo(String main_photo) {
		this.main_photo = main_photo;
	}

	public String getMain_photo_path() {
		return main_photo_path;
	}

	public void setMain_photo_path(String main_photo_path) {
		this.main_photo_path = main_photo_path;
	}

	public int getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDate created_date) {
		this.created_date = created_date;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public Integer getView_count() {
		return view_count;
	}

	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}

	public Integer getTotalLove() {
		return totalLove;
	}

	public void setTotalLove(Integer totalLove) {
		this.totalLove = totalLove;
	}

	public List<Long> getRecipeImgIds() {
		return recipeImgIds;
	}

	public void setRecipeImgIds(List<Long> recipeImgIds) {
		this.recipeImgIds = recipeImgIds;
	}

	public static ModelMapper getModelmapper() {
		return modelmapper;
	}

	public static void setModelmapper(ModelMapper modelmapper) {
		RecipeFormDto.modelmapper = modelmapper;
	}

	//private List<RecipeImgDto> recipeImgDtoList = new ArrayList<>();
	private List<Long> recipeImgIds = new ArrayList<>();
	
	private static ModelMapper modelmapper = new ModelMapper();
	
	//DTO -> Entity
	public Recipe createRecipe() {
		return modelmapper.map(this, Recipe.class);
	}
	
	//Entity -> DTO
	public static RecipeFormDto of(Recipe recipe) {
		return modelmapper.map(recipe, RecipeFormDto.class);
	}
}
