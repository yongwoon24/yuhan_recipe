package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Ingredient {
	@Id
	private int ingredient_id;
	private String ingredient_name;
	
	public int getIngredient_id() {
		return ingredient_id;
	}
	public void setIngredient_id(int ingredient_id) {
		this.ingredient_id = ingredient_id;
	}
	public String getIngredient_name() {
		return ingredient_name;
	}
	public void setIngredient_name(String ingredient_name) {
		this.ingredient_name = ingredient_name;
	}
	
	@Override
	public String toString() {
		return "Ingredient [ingredient_id=" + ingredient_id + ", ingredient_name=" + ingredient_name + "]";
	}
	
	
	
	
}
