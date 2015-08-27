package com.meeple.cloud.hivernage.model;

public class Gabarit {

	private int gabaritId;
	private String nom;
	private int longueur;
	private int largeur;
	
	public Gabarit(String nom, int longueur, int largeur) {
		this.nom = nom;
		this.longueur = longueur;
		this.largeur = largeur;
	}

	public Gabarit(String jsonObject) {
		//TODO
	}

	public int getGabaritId() {
		return gabaritId;
	}

	public void setGabaritId(int gabaritId) {
		this.gabaritId = gabaritId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getLongueur() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}
	
	
}
