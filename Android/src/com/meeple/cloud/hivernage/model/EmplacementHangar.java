package com.meeple.cloud.hivernage.model;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.db.annotation.OneToOne;

public class EmplacementHangar extends Entity<EmplacementHangar>{

	@Id
	@Column
	private int emplacementId;
	@Column
	private int posX;
	@Column
	private int posY;
	@Column
	private double angle;
	
	@OneToOne(colName = "HANGAR_ID")
	private Hangar hangar;
	
	public EmplacementHangar() {}

	public int getEmplacementId() {
		return emplacementId;
	}

	public void setEmplacementId(int emplacementId) {
		this.emplacementId = emplacementId;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Hangar getHangar() {
		return hangar;
	}

	public void setHangar(Hangar hangar) {
		this.hangar = hangar;
	}
	
	
	
}
