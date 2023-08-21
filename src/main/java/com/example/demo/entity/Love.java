package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Love {
	@Id
	private int love_id;
	private int recipe_id;
	private String user_id;
	
	public int getLove_id() {
		return love_id;
	}
	public void setLove_id(int love_id) {
		this.love_id = love_id;
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
		return "Love [love_id=" + love_id + ", recipe_id=" + recipe_id + ", user_id=" + user_id + "]";
	}
	
	
	
	
}
