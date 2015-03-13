package com.meeple.cloud.hivernage.model;

import java.util.Date;

public class EmplacementCamping {

	private String emplacement;
	private Date entree;
	private Date sortie;
	private Caravane caravane;
	
	
	public EmplacementCamping(String emplacement, Caravane caravane) {
		this.emplacement = emplacement;
		this.caravane = caravane;
	}
	
	public EmplacementCamping(String jsonObject) {
		//TODO
	}
}
