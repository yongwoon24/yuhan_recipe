package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
@Entity
public class Board {
   @Id
   private int postId;
   private String title;
   @Column(name = "content", columnDefinition = "TEXT")
   private String content;
   private LocalDateTime created_date;
   private String user_id;
   private String nickname;
    
   public int getPostId() {
      return postId;
   }
   public void setPostId(int PostId) {
      postId = PostId;
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
   public LocalDateTime getCreated_date() {
      return created_date;
   }
   public void setCreated_date(LocalDateTime created_date) {
      this.created_date = created_date;
   }
   public String getUser_id() {
      return user_id;
   }
   public void setUser_id(String user_id) {
      this.user_id = user_id;
   }
   public String getNickname() {
      return nickname;
   }
   public void setNickname(String nickname) {
      this.nickname = nickname;
   }
   
   @Override
   public String toString() {
      return "Board [PostId=" + postId + ", title=" + title + ", content=" + content + ", created_date="
            + created_date + ", user_id=" + user_id + ", nickname=" + nickname + "]";
   }
   
   
   
   
   
   
   
}