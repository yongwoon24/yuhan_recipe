package com.example.demo.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "useractivity")
public class Love {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="activity_id")
    private Long activityId;
    
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    
    @Column(name="activity")
    private String activity;
    
    @Column(name="date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    
    @ManyToOne
    @JoinColumn(name = "postId")
    private Board board;
    
    private boolean Token;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	@PrePersist
	protected void onCreate1() {
		LocalDateTime now = LocalDateTime.now().withNano(0);
	    this.setDate(now);
    }

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public boolean isToken() {
		return Token;
	}

	public void setToken(boolean token) {
		Token = token;
	}
}
