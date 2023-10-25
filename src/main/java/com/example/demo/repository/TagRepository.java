package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Recipe;
import com.example.demo.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, String>{
	List<Tag> findByRecipe(Recipe recipe);
}
