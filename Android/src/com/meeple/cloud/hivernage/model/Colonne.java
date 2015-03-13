package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.enums.ColonneType;

public class Colonne {

	private int colonneId;
	private String nom;
	private int index;
	private ColonneType type;
	
	private ArrayList<Caravane> caravanes;

	public Colonne(String nom, int index, ColonneType type) {
		this.nom = nom;
		this.index = index;
		this.type = type;
		caravanes = new ArrayList<Caravane>();
	}
	
	public Colonne(String jsonObject) {
		//TODO
	}

	public void addCaravane(Caravane c) {
		caravanes.add(c);
	}
	
	public void removeCaravane(Caravane c) {
		caravanes.remove(c);
	}
	
	public int getColonneId() {
		return colonneId;
	}

	public void setColonneId(int colonneId) {
		this.colonneId = colonneId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ColonneType getType() {
		return type;
	}

	public void setType(ColonneType type) {
		this.type = type;
	}

	public ArrayList<Caravane> getCaravanes() {
		return caravanes;
	}

	public void setCaravanes(ArrayList<Caravane> caravanes) {
		this.caravanes = caravanes;
	}
	
	
}
