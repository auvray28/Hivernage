package com.meeple.cloud.hivernage.model;

import com.meeple.cloud.hivernage.model.enums.CaravaneSens;
import com.meeple.cloud.hivernage.model.enums.CaravaneStatus;

public class Caravane {

	private int caravaneId;
	private String plaque;
	private CaravaneStatus status;
	private String observation;
	private Gabari gabari;
	private CaravaneSens sens;
	private Client client;

	
	public Caravane(String plaque, String observation, Gabari gabari,
			Client client, CaravaneSens sens) {
		super();
		this.plaque = plaque;
		this.observation = observation;
		this.gabari = gabari;
		this.client = client;
		this.sens = sens;
		this.status = CaravaneStatus.ATTENTE;
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
		if(client.getCaravane() == null) {
			client.setCaravane(this);
		}
	}

	public CaravaneSens getSens() {
		return sens;
	}

	public void setSens(CaravaneSens sens) {
		this.sens = sens;
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
	
	
}
