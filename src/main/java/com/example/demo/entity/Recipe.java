package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.formdto.RecipeFormDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Recipe {
	@Id
	@Column(name = "recipe_id") // Column 매핑 추가
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int recipe_id;
	public List<Recipe_Ingredient> getRecipeIngredients() {
		return recipeIngredients;
	}
	public void setRecipeIngredients(List<Recipe_Ingredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	private String title;
	private String main_photo;
	
	@Column(name = "created_date")
	private LocalDateTime createddate;
	
	//private String user_id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "category_name")
	private String categoryName;

	
	@Column(name = "total_love")
	private Integer totalLove=0;
	
	@Column(name = "view_count")
	private Integer viewcount = 0;
	
	private Integer dailyLove;
	private Integer weeklyLove;
	private Integer monthlyLove;
	
	private String nickname;
	

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	private String main_photo_path;
	@Column(name = "recipe_subtext")
	private String recipesubtxt;
	
	//@OneToMany(mappedBy = "love", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany
	@JoinColumn(name = "recipe_id")
	private List<Love> loves = new ArrayList<>();
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Recipe_Ingredient> recipeIngredients;
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Step> steps;
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Tag> tag;
	
	public LocalDateTime getCreateddate() {
		return createddate;
	}
	public void setCreateddate(LocalDateTime createddate) {
		this.createddate = createddate;
	}
	public Integer getViewcount() {
		return viewcount;
	}
	public void setViewcount(Integer viewcount) {
		this.viewcount = viewcount;
	}
	public List<Tag> getTag() {
		return tag;
	}
	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}
	public List<Step> getSteps() {
		return steps;
	}
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
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
	public LocalDateTime getCreated_date() {
		return createddate;
	}
	public void setCreated_date(LocalDateTime createddate) {
		this.createddate = createddate;
	}
//	public String getUser_id() {
//		return user_id;
//	}
//	public void setUser_id(String user_id) {
//		this.user_id = user_id;
//	}
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
		return viewcount;
	}
	public void setView_count(Integer viewcount) {
		this.viewcount = viewcount;
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
		return "Recipe [recipe_id=" + recipe_id + ", title=" + title + ", main_photo=" + main_photo + ", createddate="
				+ createddate + ", user=" + user + ", categoryName=" + categoryName + ", totalLove=" + totalLove
				+ ", viewcount=" + viewcount + ", dailyLove=" + dailyLove + ", weeklyLove=" + weeklyLove
				+ ", monthlyLove=" + monthlyLove + ", nickname=" + nickname + ", main_photo_path=" + main_photo_path
				+ ", recipesubtxt=" + recipesubtxt + ", loves=" + loves + ", recipeIngredients=" + recipeIngredients
				+ ", steps=" + steps + ", tag=" + tag + "]";
	}
	
	public void updateRecipe(RecipeFormDto recipeFormDto) {
		this.recipe_id=recipeFormDto.getRecipe_id();
		this.title=recipeFormDto.getTitle();
		//this.createddate=recipeFormDto.getCreated_date();
		//this.user_id=recipeFormDto.getUser_id();
		this.categoryName=recipeFormDto.getCategory_name();
		this.viewcount=recipeFormDto.getView_count();
		this.totalLove=recipeFormDto.getTotalLove();
	}
	
	@PrePersist
    protected void onCreate() {
		LocalDateTime now = LocalDateTime.now().withNano(0);
	    this.setCreated_date(now);
        
    }
	

}
	




	
	
	
	
