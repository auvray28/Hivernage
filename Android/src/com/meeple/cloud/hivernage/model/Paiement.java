package com.meeple.cloud.hivernage.model;

import java.util.Date;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.model.enums.FactureMdp;

public class Paiement extends Entity<Paiement> {

	@Id
	@Column
	private int paiementId;
	
	@Column
	private Date date;
	
	@Column
	private FactureMdp mode;
	
	@Column
	private int value;
	
	public Paiement() {}

	
	public Paiement(Date date, FactureMdp mode, int value) {
		super();
		this.date = date;
		this.mode = mode;
		this.value = value;
	}


	public int getPaiementId() {
		return paiementId;
	}

	public void setPaiementId(int paiementId) {
		this.paiementId = paiementId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public FactureMdp getMode() {
		return mode;
	}

	public void setMode(FactureMdp mode) {
		this.mode = mode;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString() {
		return "Le "+ date + " payment de " + value + " par " + mode;
	}
	
}
