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
	
	@Column(nullable = false)
	private String main_photo;
	private LocalDate created_date;
	private String user_id;
	private String category_name;
	private Integer view_count = 0;
	public String getMain_photo_oriname() {
		return main_photo_oriname;
	}
	public void recipeRepository(String main_photo_oriname) {
		this.main_photo_oriname = main_photo_oriname;
	}
	public void setMain_photo_oriname(String main_photo_oriname) {
		this.main_photo_oriname = main_photo_oriname;
	}
	public int getMain_photo_no() {
		return main_photo_no;
	}
	public void setMain_photo_no(int main_photo_no) {
		this.main_photo_no = main_photo_no;
	}
	public String getMain_photo_url() {
		return main_photo_url;
	}
	public void setMain_photo_url(String main_photo_url) {
		this.main_photo_url = main_photo_url;
	}

	@Column(nullable = false)
	private String main_photo_oriname;
	@Column(nullable = false)
	private int main_photo_no;
	@Column(nullable = false)
	private String main_photo_url;
	
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
	
	@Override
	public String toString() {
		return "Recipe [recipe_id=" + recipe_id + ", title=" + title + ", main_photo=" + main_photo + ", created_date="
				+ created_date + ", user_id=" + user_id + ", category_name=" + category_name + ", view_count="
				+ view_count + ", main_photo_oriname=" + main_photo_oriname + ", main_photo_no=" + main_photo_no
				+ ", main_photo_url=" + main_photo_url + "]";
	}
	
	
	
	
}
