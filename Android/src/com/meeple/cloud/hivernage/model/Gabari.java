package com.meeple.cloud.hivernage.model;

public class Gabari {

	private int gabariId;
	private double prix;
	private String nom;
	private int longueur;

	public Gabari(double prix, String nom, int longueur) {
		this.prix = prix;
		this.nom = nom;
		this.longueur = longueur;
	}

	public Gabari(String jsonObject) {
		//TODO
	}
}
