package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Recipe_Ingredient {
	@Id
	private int r_id;
	private int recipe_id;
	private int ingredient_id;
	
	public int getR_id() {
		return r_id;
	}
	public void setR_id(int r_id) {
		this.r_id = r_id;
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public int getIngredient_id() {
		return ingredient_id;
	}
	public void setIngredient_id(int ingredient_id) {
		this.ingredient_id = ingredient_id;
	}
	
	@Override
	public String toString() {
		return "RecipeIngredient [r_id=" + r_id + ", recipe_id=" + recipe_id + ", ingredient_id=" + ingredient_id + "]";
	}
	
	

}
