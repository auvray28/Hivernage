package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;
import java.util.Iterator;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.GsonTransient;
import com.meeple.cloud.hivernage.db.annotation.Transient;

public class Hangar extends Entity<Hangar>{

	@Column
	private int hangarId;
	@Column
	private String nom;
	@Column
	private int longueur;
	@Column
	private int largeur;
	
	@GsonTransient
	@Transient
	private ArrayList<Caravane> caravanes;

	public Hangar() {
		this.caravanes = new ArrayList();
	}
	
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
	
	public void addCaravane(Caravane caravane) {
		Iterator<Caravane> it = caravanes.iterator();
		boolean alreadyHave = false;
		
		while(it.hasNext()) {
			Caravane c = it.next();
			if(c.getPlaque().equalsIgnoreCase(caravane.getPlaque())) {
				alreadyHave = true;
			}
		}
		
		if(!alreadyHave) {
			caravanes.add(caravane);
		}
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
	
	public String toString() {
		return getNom() + " : " + getCaravanes().size();
	}

	// Déplace TOUTES les caravanes dans le hangar passé en parametre
	public void relocateCaravanes(Hangar toMove) {
		Iterator<Caravane> it = caravanes.iterator();
		while(it.hasNext()) {
			Caravane c = it.next();
			it.remove();
			toMove.addCaravane(c);
		}
	}
	
}
