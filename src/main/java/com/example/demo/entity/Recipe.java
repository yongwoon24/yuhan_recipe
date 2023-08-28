package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Recipe {
	@Id
	@Column(name = "recipe_id") // Column 매핑 추가
	private int recipe_id;
	private String title;
	private String main_photo;
	private LocalDate created_date;
	private String user_id;
	private String categoryName;
	private Integer view_count = 0;
	private Integer totalLove;
	private String main_photo_path;

	
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
	public String getMain_photo() {
		return main_photo;
	}
	public void setMain_photo(String main_photo) {
		this.main_photo = main_photo;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategory_name(String categoryName) {
		this.categoryName = categoryName;
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
	public String getMain_photo_path() {
		return main_photo_path;
	}
	public void setMain_photo_path(String main_photo_path) {
		this.main_photo_path = main_photo_path;
	}
	@Override
	public String toString() {
		return "Recipe [recipe_id=" + recipe_id + ", title=" + title + ", main_photo=" + main_photo + ", created_date="
				+ created_date + ", user_id=" + user_id + ", categoryName=" + categoryName + ", view_count="
				+ view_count + ", totalLove=" + totalLove + ", main_photo_path=" + main_photo_path + "]";
	}
	
	
	
	
	

}
