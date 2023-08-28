package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Comment {
	@Id
	private int comment_id;
	private int post_id;
	private String user_id;
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
	private LocalDateTime created_date;
	
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCreated_date() {
		return created_date;
	}
	public void setCreated_date(LocalDateTime created_date) {
		this.created_date = created_date;
	}
	
	@Override
	public String toString() {
		return "Comment [comment_id=" + comment_id + ", post_id=" + post_id + ", user_id=" + user_id + ", content="
				+ content + ", created_date=" + created_date + "]";
	}
	
	
}
