package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Cookingstep {
	@Id
	private int step_id;
	private int recipe_id;
	@Column(name = "step_content", columnDefinition = "TEXT")
	private String step_content;
	private String step_photo;
	
	public int getStep_id() {
		return step_id;
	}
	public void setStep_id(int step_id) {
		this.step_id = step_id;
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public String getStep_content() {
		return step_content;
	}
	public void setStep_content(String step_content) {
		this.step_content = step_content;
	}
	public String getStep_photo() {
		return step_photo;
	}
	public void setStep_photo(String step_photo) {
		this.step_photo = step_photo;
	}
	
	@Override
	public String toString() {
		return "Cookingstep [step_id=" + step_id + ", recipe_id=" + recipe_id + ", step_content=" + step_content
				+ ", step_photo=" + step_photo + "]";
	}
	
	
	
}
