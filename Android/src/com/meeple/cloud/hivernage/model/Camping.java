package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;

public class Camping {

	private int CampingId;
	private String nom;
	private String mail;
	private String tel;
	private double prix;
	
	private ArrayList<EmplacementCamping> emplacements;

	public Camping(String nom, String mail, String tel, double prix) {
		this.nom = nom;
		this.mail = mail;
		this.tel = tel;
		this.prix = prix;
		this.emplacements = new ArrayList<EmplacementCamping>();
		
	}
	
	public Camping(String jsonObject) {
		//TODO
	}

	public int getCampingId() {
		return CampingId;
	}

	public void setCampingId(int campingId) {
		CampingId = campingId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public ArrayList<EmplacementCamping> getEmplacements() {
		return emplacements;
	}

	public void setEmplacements(ArrayList<EmplacementCamping> emplacements) {
		this.emplacements = emplacements;
	}
	
	
}
