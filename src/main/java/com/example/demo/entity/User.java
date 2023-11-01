package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String user_id;
	private String name;
	private String email;
	private String password;
	private LocalDate birthdate;
	private String phone_number;
	private String nickname;
	private String userphotopath;
	private String userpr;
	private Integer usertotallikes;
	
	@Column(length = 36, name = "verification_token")
    private String verificationToken;

    private boolean emailVerified;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Recipe> recipe;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
	

	public String getUserphotopath() {
		return userphotopath;
	}

	public void setUserphotopath(String userphotopath) {
		this.userphotopath = userphotopath;
	}

	
	
	public String getUserpr() {
		return userpr;
	}

	public void setUserpr(String userpr) {
		this.userpr = userpr;
	}
	

	public Integer getUsertotallikes() {
		return usertotallikes;
	}

	public void setUsertotallikes(Integer usertotallikes) {
		this.usertotallikes = usertotallikes;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", birthdate=" + birthdate + ", phone_number=" + phone_number + ", nickname=" + nickname
				+ ", verificationToken=" + verificationToken + ", emailVerified=" + emailVerified + "]";
	}


    

}
