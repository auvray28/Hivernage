package com.meeple.cloud.hivernage.model;

import java.util.Date;

public class EmplacementCamping {

	private int emplacementId;
	private String emplacement;
	private Date entree;
	private Date sortie;
	private Camping camping;
	
	public EmplacementCamping(String emplacement, Caravane caravane) {
		this.emplacement = emplacement;
	}
	
	public EmplacementCamping(String jsonObject) {
		//TODO
	}

	public String getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

	public Date getEntree() {
		return entree;
	}

	public void setEntree(Date entree) {
		this.entree = entree;
	}

	public Date getSortie() {
		return sortie;
	}

	public void setSortie(Date sortie) {
		this.sortie = sortie;
	}

	public Camping getCamping() {
		return camping;
	}

	public void setCamping(Camping camping) {
		this.camping = camping;
	}
	
	
}
