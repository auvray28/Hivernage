package com.meeple.cloud.hivernage.model;

import com.meeple.cloud.hivernage.model.enums.CaravaneStatus;

public class Caravane {

	private int caravaneId;
	private String plaque;
	private CaravaneStatus status;
	private String observation;
	private Gabari gabari;
	
	private Client client;

	public Caravane(String plaque, Client client) {
		super();
		this.plaque = plaque;
		this.status = CaravaneStatus.ATTENTE;
		this.client = client;
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

	public Gabari getGabari() {
		return gabari;
	}

	public void setGabari(Gabari gabari) {
		this.gabari = gabari;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	
}
