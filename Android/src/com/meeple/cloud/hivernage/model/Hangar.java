package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Hangar {

	private int hangarId;
	private String nom;
	private int longueur;
	private int largeur;
	
	private ArrayList<Caravane> caravanes;

	public Hangar(String nom, int longueur, int largeur) {
		super();
		this.nom = nom;
		this.longueur = longueur;
		this.largeur = largeur;
		this.caravanes = new ArrayList<Caravane>();
	}
	
	public Hangar(String jsonObject) {
		//TODO
	}

	public int getHangarId() {
		return hangarId;
	}

	public void setHangarId(int hangarId) {
		this.hangarId = hangarId;
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

	public ArrayList<Caravane> getCaravanes() {
		return caravanes;
	}

	public void setCaravanes(ArrayList<Caravane> caravanes) {
		this.caravanes = caravanes;
	}
	
	public void removeCaravane(Caravane caravane) {
		Iterator<Caravane> it = caravanes.iterator();
		while(it.hasNext()) {
			Caravane c = it.next();
			if(c.getPlaque().equalsIgnoreCase(caravane.getPlaque())) {
				it.remove();
			}
		}
	}
	
	
}
