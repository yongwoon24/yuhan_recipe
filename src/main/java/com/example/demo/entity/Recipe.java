package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Recipe {
	@Id
	private int recipe_id;
	private String title;
	private String recipe_introduce;
	private String main_photo;
	private String created_date;
	private String user_id;
	private String category_name;
	
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
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
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
	public String getRecipe_introduce() {
		return recipe_introduce;
	}
	public void setRecipe_introduce(String recipe_introduce) {
		this.recipe_introduce = recipe_introduce;
	}
	
	@Override
	public String toString() {
		return "Recipe [recipe_id=" + recipe_id + ", title=" + title + ", recipe_introduce=" + recipe_introduce
				+ ", main_photo=" + main_photo + ", created_date=" + created_date + ", user_id=" + user_id
				+ ", category_name=" + category_name + "]";
	}
	
}
