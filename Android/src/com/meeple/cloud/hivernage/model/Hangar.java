package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;

public class Hangar {

	private int hangarId;
	private String nom;
	private int longueur;
	
	private ArrayList<Caravane> caravanes;

	public Hangar(String nom, int longueur) {
		super();
		this.nom = nom;
		this.longueur = longueur;
		this.caravanes = new ArrayList<Caravane>();
	}
	
	public Hangar(String jsonObject) {
		//TODO
	}
	
}
