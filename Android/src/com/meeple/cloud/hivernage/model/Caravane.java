package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.db.annotation.OneToMany;
import com.meeple.cloud.hivernage.db.annotation.OneToOne;
import com.meeple.cloud.hivernage.model.enums.CaravaneStatus;

public class Caravane extends Entity<Caravane>{

	@Id
	@Column
	private int caravaneId;
	@Column
	private String plaque;
	@Column
	private CaravaneStatus status;
	@Column
	private String observation;
	
	@OneToOne(colName="GABARAIT_ID")
	private Gabarit gabari;
	
	@OneToOne(ref="caravane")
	private Client client;
	
	@OneToOne(colName="EMP_HANGAR_ID")
	private EmplacementHangar emplacementHangar;
	
	@OneToMany(colName="EMP_CAMPING_ID")
	private ArrayList<EmplacementCamping> emplacementCamping;
	
	public Caravane() {}
	
	public Caravane(String plaque, String observation, Gabarit gabari,
			Client client) {
		super();
		this.plaque = plaque;
		this.observation = observation;
		this.gabari = gabari;
		this.client = client;
		this.status = CaravaneStatus.ATTENTE;
		this.emplacementHangar = null;
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


	public ArrayList<EmplacementCamping> getEmplacementCamping() {
		return emplacementCamping;
	}
	
	
	public Camping getCurrentCamping(){
		//NICO comme nous avons une arrayliste d'emplacement camping comment on sait dans qu'elle camping on est a une date d ?
		//
		// il faut check toute les emplacementCamping au load et recup le currentCamping 
		
		
		return null;
	}

	public EmplacementHangar getEmplacementHangar() {
		return emplacementHangar;
	}

	public void setEmplacementHangar(EmplacementHangar hangar) {
		this.emplacementHangar = hangar;
	}

	public void setEmplacementCamping(
			ArrayList<EmplacementCamping> emplacementCamping) {
		this.emplacementCamping = emplacementCamping;
	}
	
}
