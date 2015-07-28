package com.meeple.cloud.hivernage.model;

import java.util.Date;

public class Facture {
	private int factureId;
	private Client client;
	private int ttc;
	private int ht;
	private Date dateEdition;
	private int remise;
	
	
	public Facture(int factureId, Client client, int ttc, int ht,
			Date dateEdition, int remise) {
		super();
		this.factureId = factureId;
		this.client = client;
		this.ttc = ttc;
		this.ht = ht;
		this.dateEdition = dateEdition;
		this.remise = remise;
	}


	public int getFactureId() {
		return factureId;
	}


	public void setFactureId(int factureId) {
		this.factureId = factureId;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


	public int getTtc() {
		return ttc;
	}


	public void setTtc(int ttc) {
		this.ttc = ttc;
	}


	public int getHt() {
		return ht;
	}


	public void setHt(int ht) {
		this.ht = ht;
	}


	public Date getDateEdition() {
		return dateEdition;
	}


	public void setDateEdition(Date dateEdition) {
		this.dateEdition = dateEdition;
	}


	public int getRemise() {
		return remise;
	}


	public void setRemise(int remise) {
		this.remise = remise;
	}
	
	
	
}
