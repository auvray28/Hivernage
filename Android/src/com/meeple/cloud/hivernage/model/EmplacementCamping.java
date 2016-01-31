package com.meeple.cloud.hivernage.model;

import java.util.Date;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.db.annotation.ManyToOne;

public class EmplacementCamping extends Entity<EmplacementCamping>{

	@Id
	@Column
	private int emplacementId;
	@Column
	private String emplacement;
	@Column
	private Date entree;
	@Column
	private Date sortie;
	
	@ManyToOne(colName = "CAMPING_ID")
	private Camping camping;
	
	//NICO il faut un @ ici
	private Caravane caravane;
	
	public EmplacementCamping() {}
	
	public EmplacementCamping(String emplacement, Caravane caravane) {
		this.emplacement = emplacement;
		this.caravane    = caravane;
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

	public Caravane getCaravane() {
		return caravane;
	}

	public void setCaravane(Caravane caravane) {
		this.caravane = caravane;
	}
	
}
