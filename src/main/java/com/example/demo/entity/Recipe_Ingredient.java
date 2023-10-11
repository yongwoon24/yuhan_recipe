package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Recipe_Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int r_id;
	
	@ManyToOne
	@JoinColumn(name="recipe_id")
	Recipe recipe;
	
	@ManyToOne
	@JoinColumn(name="ingredient_id")
	Ingredient ingredient;
	
	private String mensuration;
	
	public String getMensuration() {
		return mensuration;
	}
	public void setMensuration(String mensuration) {
		this.mensuration = mensuration;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	public int getR_id() {
		return r_id;
	}
	public void setR_id(int r_id) {
		this.r_id = r_id;
	}
	@Override
	public String toString() {
		return "Recipe_Ingredient [r_id=" + r_id + ", recipe=" + recipe + ", ingredient=" + ingredient
				+ ", mensuration=" + mensuration + "]";
	}

	

	

}
