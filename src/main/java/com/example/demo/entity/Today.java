package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Today {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int tID;

@OneToOne
@JoinColumn(name="user_id")
private User user;

private int no1;
private int no2;
private int no3;
private int no4;
private int no5;
private int no6;
private int no7;
private int no8;
private int no9;
private int no10;
public int gettID() {
	return tID;
}
public void settID(int tID) {
	this.tID = tID;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public int getNo1() {
	return no1;
}
public void setNo1(int no1) {
	this.no1 = no1;
}
public int getNo2() {
	return no2;
}
public void setNo2(int no2) {
	this.no2 = no2;
}
public int getNo3() {
	return no3;
}
public void setNo3(int no3) {
	this.no3 = no3;
}
public int getNo4() {
	return no4;
}
public void setNo4(int no4) {
	this.no4 = no4;
}
public int getNo5() {
	return no5;
}
public void setNo5(int no5) {
	this.no5 = no5;
}
public int getNo6() {
	return no6;
}
public void setNo6(int no6) {
	this.no6 = no6;
}
public int getNo7() {
	return no7;
}
public void setNo7(int no7) {
	this.no7 = no7;
}
public int getNo8() {
	return no8;
}
public void setNo8(int no8) {
	this.no8 = no8;
}
public int getNo9() {
	return no9;
}
public void setNo9(int no9) {
	this.no9 = no9;
}
public int getNo10() {
	return no10;
}
public void setNo10(int no10) {
	this.no10 = no10;
}


}
