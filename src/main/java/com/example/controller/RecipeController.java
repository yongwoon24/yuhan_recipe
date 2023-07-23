package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dao.RecipeDAO;
import com.example.vo.RecipeEntity;

public class RecipeController {
	
	private Logger logger = LoggerFactory.getLogger(RecipeController.class);
	
	@Autowired
	private RecipeDAO newDAO;
	
	@RequestMapping("/recipe")
	public @ResponseBody Map<String, Object> recipe() throws Exception {
		Map<String, Object> rtnObj = new HashMap<> ();
	
	
	List<RecipeEntity> recipeList = newDAO.listRecipe();
	
	logger.info("recipe->" + recipeList.toString());
	
	rtnObj.put("recipe_list", recipeList);
	
	return rtnObj;
	}
}
