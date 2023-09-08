package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.formdto.RecipeFormDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Recipe {
	@Id
	@Column(name = "recipe_id") // Column 매핑 추가
	private int recipe_id;
	private String title;
	private String main_photo;
	private LocalDate created_date;
	
	private String user_id;
//	@OneToMany
//	@JoinColumn(name = "user_id")
//	private List<User> users = new ArrayList<>();
	
	@Column(name = "category_name")
	private String categoryName;

	
	@Column(name = "total_love")
	private Integer totalLove=0;

	private Integer view_count = 0;
	
	private Integer dailyLove;
	private Integer weeklyLove;
	private Integer monthlyLove;
	

	private String main_photo_path;
	@Column(name = "recipe_subtext")
	private String recipesubtxt;
	
	//@OneToMany(mappedBy = "love", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany
	@JoinColumn(name = "recipe_id")
	private List<Love> loves = new ArrayList<>();
	
	public String getRecipesubtxt() {
		return recipesubtxt;
	}
	public void setRecipesubtxt(String recipesubtxt) {
		this.recipesubtxt = recipesubtxt;
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
//	public List<User> getUser() {
//		return users;
//	}
//	public void setUser(List<User> users) {
//		this.users = users;
//	}
	public List<Love> getLoves() {
		return loves;
	}
	public void setLoves(List<Love> loves) {
		this.loves = loves;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	
	public Integer getDailyLove() {
		return dailyLove;
	}
	public void setDailyLove(Integer dailyLove) {
		this.dailyLove = dailyLove;
	}
	public Integer getWeeklyLove() {
		return weeklyLove;
	}
	public void setWeeklyLove(Integer weeklyLove) {
		this.weeklyLove = weeklyLove;
	}
	public Integer getMonthlyLove() {
		return monthlyLove;
	}
	public void setMonthlyLove(Integer monthlyLove) {
		this.monthlyLove = monthlyLove;
	}
	@Override
	public String toString() {
		return "Recipe [recipe_id=" + recipe_id + ", title=" + title + ", main_photo=" + main_photo + ", created_date="
				+ created_date + ", user_id="  + ", categoryName=" + categoryName + ", view_count="
				+ view_count + ", totalLove=" + totalLove + ", main_photo_path=" + main_photo_path + ", recipesubtxt="
				+ recipesubtxt + "]";
	}
	
	public void updateRecipe(RecipeFormDto recipeFormDto) {
		this.recipe_id=recipeFormDto.getRecipe_id();
		this.title=recipeFormDto.getTitle();
		this.created_date=recipeFormDto.getCreated_date();
		//this.user_id=recipeFormDto.getUser_id();
		this.categoryName=recipeFormDto.getCategory_name();
		this.view_count=recipeFormDto.getView_count();
		this.totalLove=recipeFormDto.getTotalLove();
	}
	
	@PrePersist
    protected void onCreate() {
        created_date = LocalDate.now();
    }
	

}
	




	
	
	
	

