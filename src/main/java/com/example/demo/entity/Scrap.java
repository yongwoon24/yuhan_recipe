package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Scrap {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scrap_id;
	
	@ManyToOne
	@JoinColumn(name="recipe_id")
	private Recipe recipe;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    User user;
	
	public int getScrap_id() {
		return scrap_id;
	}
	public void setScrap_id(int scrap_id) {
		this.scrap_id = scrap_id;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Scrap [scrap_id=" + scrap_id + ", recipe=" + recipe + ", user=" + user + "]";
	}
	
	
	
	

}
