package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Love {
	@Id
	private int like_id;
	private int recipe_id;
	private String user_id;
	
	public int getLike_id() {
		return like_id;
	}
	public void setLike_id(int like_id) {
		this.like_id = like_id;
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		return "Love [like_id=" + like_id + ", recipe_id=" + recipe_id + ", user_id=" + user_id + "]";
	}
	
	
	
	
	
}
