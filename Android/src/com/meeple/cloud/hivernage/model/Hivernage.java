package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;
import java.util.Date;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.db.annotation.OneToMany;
import com.meeple.cloud.hivernage.model.enums.FactureMdp;
import com.meeple.cloud.hivernage.model.enums.HivernageStatus;

public class Hivernage extends Entity<Hivernage>{

	@Id
	@Column
	private int hivernageId;
	@Column
	private HivernageStatus status;
	@Column
	private int acompte;
	@Column
	private Date debut;
	@Column
	private Date fin;
	
	@OneToMany(colName="HIVERNAGE_ID")
	private ArrayList<Paiement> historiquePayement;
	
	public Hivernage() {}
	
	public Hivernage(HivernageStatus status, int acompte) {
		super();
		this.status = status;
		this.acompte = acompte;
		debut = new Date();
		
		historiquePayement = new ArrayList<Paiement>();
	}
	
	public Hivernage(int acompte) {
		super();
		this.acompte = acompte;
		debut = new Date();
		
		historiquePayement = new ArrayList<Paiement>();
		
		this.status = (acompte == 0) ? HivernageStatus.PAYE : (acompte < 0) ? HivernageStatus.IMPAYE : HivernageStatus.ACCOMPTE;
	}
	
	public Hivernage(String jsonObject) {
		//TODO
	}

	public int getHivernageId() {
		return hivernageId;
	}

	public void setHivernageId(int hivernageId) {
		this.hivernageId = hivernageId;
	}

	public HivernageStatus getStatus() {
		return status;
	}

	public void setStatus(HivernageStatus status) {
		this.status = status;
	}

	public int getAcompte() {
		return acompte;
	}

	public void setAcompte(int acompte) {
		this.acompte = acompte;
	}
	
	public void addPayement(Date d, FactureMdp type, int value) {
		acompte += value;
		Paiement p = new Paiement(d, type, value);
		historiquePayement.add(p);
	}
	
	public Date getDebut() {
		return debut;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}
}
