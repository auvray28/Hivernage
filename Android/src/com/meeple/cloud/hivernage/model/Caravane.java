package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;

import android.graphics.Point;

import com.meeple.cloud.hivernage.model.enums.CaravaneStatus;

public class Caravane {

	private int caravaneId;
	private String plaque;
	private CaravaneStatus status;
	private String observation;
	private Gabarit gabari;
	private Client client;
	private Point coord;
	private float angle;
	private Hangar hangar;
	private ArrayList<EmplacementCamping> emplacementCamping;
	
	public Caravane(String plaque, String observation, Gabarit gabari,
			Client client) {
		super();
		this.plaque = plaque;
		this.observation = observation;
		this.gabari = gabari;
		this.client = client;
		this.status = CaravaneStatus.ATTENTE;
		this.coord = new Point(0, 0);
		this.angle = 0;
		this.hangar = null;
		this.emplacementCamping = new ArrayList<EmplacementCamping>();
		
		if(client != null && client.getCaravane() == null ) {
			client.setCaravane(this);
		}
	}

	public Caravane(String jsonObject) {
		//TODO 
	}

	public int getCaravaneId() {
		return caravaneId;
	}

	public void setCaravaneId(int caravaneId) {
		this.caravaneId = caravaneId;
	}

	public String getPlaque() {
		return plaque;
	}

	public void setPlaque(String plaque) {
		this.plaque = plaque;
	}

	public CaravaneStatus getStatus() {
		return status;
	}

	public void setStatus(CaravaneStatus status) {
		this.status = status;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Gabarit getGabari() {
		return gabari;
	}

	public void setGabari(Gabarit gabari) {
		this.gabari = gabari;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
		if(client.getCaravane() == null) {
			client.setCaravane(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + caravaneId;
		result = prime * result + ((plaque == null) ? 0 : plaque.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Caravane other = (Caravane) obj;
		if (caravaneId != other.caravaneId) {
			return false;
		}
		if (plaque == null) {
			if (other.plaque != null) {
				return false;
			}
		} else if (!plaque.equals(other.plaque)) {
			return false;
		}
		return true;
	}

	public Point getCoord() {
		return coord;
	}

	public void setCoord(Point coord) {
		this.coord = coord;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Hangar getHangar() {
		return hangar;
	}

	public void setHangar(Hangar hangar) {
		this.hangar = hangar;
	}

	public ArrayList<EmplacementCamping> getEmplacementCamping() {
		return emplacementCamping;
	}
	
	
}
