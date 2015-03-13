package com.meeple.cloud.hivernage.model;

import java.util.Date;

import com.meeple.cloud.hivernage.model.enums.HivernageStatus;

public class Hivernage {

	private int hivernageId;
	private HivernageStatus status;
	private int acompte;
	private Date debut;
	private Date fin;
	
	public Hivernage(HivernageStatus status, int acompte) {
		super();
		this.status = status;
		this.acompte = acompte;
		debut = new Date();
	}
	
	public Hivernage(String jsonObject) {
		//TODO
	}
}
