package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Step {

	@Id
	private int stId;
	
	@ManyToOne
	@JoinColumn(name="reicpe_id")
	Recipe recipe;
	
	private String SContent;
	
	private String Sphoto;
	private String Sphotopath;
	private String Singtxt;
	private String Stooltxt;
	private String Scontroltxt;
	private String Stip;
	
	@Override
	public String toString() {
		return "Step [stId=" + stId + ", recipe=" + recipe + ", SContent=" + SContent + ", Sphoto=" + Sphoto
				+ ", Sphotopath=" + Sphotopath + ", Singtxt=" + Singtxt + ", Stooltxt=" + Stooltxt + ", Scontroltxt="
				+ Scontroltxt + ", Stip=" + Stip + "]";
	}
	public int getStId() {
		return stId;
	}
	public void setStId(int stId) {
		this.stId = stId;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public String getSContent() {
		return SContent;
	}
	public void setSContent(String sContent) {
		SContent = sContent;
	}
	public String getSphoto() {
		return Sphoto;
	}
	public void setSphoto(String sphoto) {
		Sphoto = sphoto;
	}
	public String getSphotopath() {
		return Sphotopath;
	}
	public void setSphotopath(String sphotopath) {
		Sphotopath = sphotopath;
	}
	public String getSingtxt() {
		return Singtxt;
	}
	public void setSingtxt(String singtxt) {
		Singtxt = singtxt;
	}
	public String getStooltxt() {
		return Stooltxt;
	}
	public void setStooltxt(String stooltxt) {
		Stooltxt = stooltxt;
	}
	public String getScontroltxt() {
		return Scontroltxt;
	}
	public void setScontroltxt(String scontroltxt) {
		Scontroltxt = scontroltxt;
	}
	public String getStip() {
		return Stip;
	}
	public void setStip(String stip) {
		Stip = stip;
	}
	
}
