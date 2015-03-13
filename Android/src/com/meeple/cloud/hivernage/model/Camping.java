package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;

public class Camping {

	private int CampingId;
	private String nom;
	private String mail;
	private String tel;
	private String ouverture;
	private String fermeture;
	private double prix;
	
	private ArrayList<EmplacementCamping> emplacements;

	public Camping(String nom, String mail, String tel, String ouverture,
			String fermeture, double prix) {
		this.nom = nom;
		this.mail = mail;
		this.tel = tel;
		this.ouverture = ouverture;
		this.fermeture = fermeture;
		this.prix = prix;
		this.emplacements = new ArrayList<EmplacementCamping>();
		
	}
	
	public Camping(String jsonObject) {
		//TODO
	}
	
	
}
