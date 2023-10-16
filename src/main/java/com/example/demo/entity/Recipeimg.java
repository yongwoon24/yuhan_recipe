package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="recipeimg")
public class Recipeimg {

	@Id
	@Column(name="recipe_pid")
	private Long recipepid;
	
	@Column(name="step_num")
	private int stepnum;
	
	@Column(name="step_photo")
	private String stepphoto;
	
	@Column(name="step_photo_path")
	private String stepphotopath;
	
	@Column(name="finish_photo")
	private String finishphoto;
	
	@Column(name="finish_photo_path")
	private String finishphotopath;
	
	@ManyToOne
	@JoinColumn(name="recipe_id")
	private Recipe recipe;

	@Override
	public String toString() {
		return "Recipeimg [recipepid=" + recipepid + ", stepnum=" + stepnum + ", stepphoto=" + stepphoto
				+ ", stepphotopath=" + stepphotopath + ", finishphoto=" + finishphoto + ", finishphotopath="
				+ finishphotopath + ", recipe=" + recipe + "]";
	}

	public Long getRecipepid() {
		return recipepid;
	}

	public void setRecipepid(Long recipepid) {
		this.recipepid = recipepid;
	}

	public int getStepnum() {
		return stepnum;
	}

	public void setStepnum(int stepnum) {
		this.stepnum = stepnum;
	}

	public String getStepphoto() {
		return stepphoto;
	}

	public void setStepphoto(String stepphoto) {
		this.stepphoto = stepphoto;
	}

	public String getStepphotopath() {
		return stepphotopath;
	}

	public void setStepphotopath(String stepphotopath) {
		this.stepphotopath = stepphotopath;
	}

	public String getFinishphoto() {
		return finishphoto;
	}

	public void setFinishphoto(String finishphoto) {
		this.finishphoto = finishphoto;
	}

	public String getFinishphotopath() {
		return finishphotopath;
	}

	public void setFinishphotopath(String finishphotopath) {
		this.finishphotopath = finishphotopath;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
