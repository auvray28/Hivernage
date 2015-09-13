package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;
import java.util.Date;

import com.meeple.cloud.hivernage.model.enums.FactureMdp;
import com.meeple.cloud.hivernage.model.enums.HivernageStatus;

public class Hivernage {

	private int hivernageId;
	private HivernageStatus status;
	private int acompte;
	private Date debut;
	private Date fin;
	
	private ArrayList<String> historiquePayement;
	
	public Hivernage(HivernageStatus status, int acompte) {
		super();
		this.status = status;
		this.acompte = acompte;
		debut = new Date();
		
		historiquePayement = new ArrayList<String>();
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
		
		historiquePayement.add("Le "+ d.toString() + " payment de " + value + " par " + type);
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
