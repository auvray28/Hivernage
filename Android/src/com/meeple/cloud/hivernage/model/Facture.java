package com.meeple.cloud.hivernage.model;

import java.util.Date;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.db.annotation.OneToOne;

public class Facture extends Entity<Facture>{
	
	@Id
	@Column
	private int factureId;
	@Column
	private int ttc;
	@Column
	private int ht;
	@Column
	private Date dateEdition;
	@Column
	private int remise;

	@OneToOne(colName = "CLIENT_ID")
	private Client client;
	
	public Facture() {}
	
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
