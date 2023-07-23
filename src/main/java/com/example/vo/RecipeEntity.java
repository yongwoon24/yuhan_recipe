package com.example.vo;

import lombok.Data;

@Data
public class RecipeEntity {
	private String title;
	private String main_photo;
	private String created_date;
	private String user_id;
	private int recipe_id;
}
