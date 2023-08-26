package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Love")
public class Love {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loveId;

    private String userId;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // Getter and Setter methods

    public Long getLoveId() {
        return loveId;
    }

    public void setLoveId(Long loveId) {
        this.loveId = loveId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
