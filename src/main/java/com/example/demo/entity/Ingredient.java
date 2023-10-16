package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ingredient_id;
	
	@Column(name="ingredient_name")
	private String ingredientName;
	
	public int getIngredient_id() {
		return ingredient_id;
	}
	public void setIngredient_id(int ingredient_id) {
		this.ingredient_id = ingredient_id;
	}
	public String getIngredient_name() {
		return ingredientName;
	}
	public void setIngredient_name(String ingredient_name) {
		this.ingredientName = ingredient_name;
	}
	
	@Override
	public String toString() {
		return "Ingredient [ingredient_id=" + ingredient_id + ", ingredient_name=" + ingredientName + "]";
	}
	
	
	
	
}
