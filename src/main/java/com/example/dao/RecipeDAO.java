package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.vo.RecipeEntity;

@Mapper
public interface RecipeDAO {
		public List<RecipeEntity> listRecipe();
}
