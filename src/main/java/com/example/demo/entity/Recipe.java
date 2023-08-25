package com.example.demo.entity;

import java.time.LocalDate;

import com.example.demo.formdto.RecipeFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "recipe")
@Getter @Setter
@ToString
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "recipe_id") // Column 매핑 추가
	private Integer recipe_id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private LocalDate created_date;
	private String user_id;
	
	@Column(name = "category_name")
	private String category_name;
	private Integer view_count = 0;
	private Integer totalLove;
	private String main_photo;
	private String main_photo_path;
	
	

	@Override
	public String toString() {
		return "Recipe [recipe_id=" + recipe_id + ", title=" + title + ", created_date=" + created_date + ", user_id="
				+ user_id + ", category_name=" + category_name + ", view_count=" + view_count + ", totalLove="
				+ totalLove + ", main_photo=" + main_photo + ", main_photo_path=" + main_photo_path + "]";
	}



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



	public void updateRecipe(RecipeFormDto recipeFormDto) {
		this.recipe_id=recipeFormDto.getRecipe_id();
		this.title=recipeFormDto.getTitle();
		this.created_date=recipeFormDto.getCreated_date();
		this.user_id=recipeFormDto.getUser_id();
		this.category_name=recipeFormDto.getCategory_name();
		this.view_count=recipeFormDto.getView_count();
		this.totalLove=recipeFormDto.getTotalLove();
	}





	
	
	
	
}
