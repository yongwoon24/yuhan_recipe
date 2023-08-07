package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Board {

	@Id
	private int post_id;
	private String title;
	private String content;
	private String created_date;
	private String user_id;
	
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "Board [post_id=" + post_id + ", title=" + title + ", content=" + content + ", created_date="
				+ created_date + ", user_id=" + user_id + "]";
	}
	
	
	public Board(int post_id, String title, String content, String created_date, String user_id) {
		super();
		this.post_id = post_id;
		this.title = title;
		this.content = content;
		this.created_date = created_date;
		this.user_id = user_id;
	}
	
	
	
}
