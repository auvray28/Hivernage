package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.GsonTransient;
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
	
	@GsonTransient
	@OneToOne(ref="caravane")
	private Client client;
	
	@OneToOne(colName="EMP_HANGAR_ID")
	private EmplacementHangar emplacementHangar;
	
	@OneToMany(colName="EMP_CAMPING_ID")
	private ArrayList<EmplacementCamping> emplacementCamping;
	
	public Caravane() {
		this.emplacementCamping = new ArrayList<EmplacementCamping>();
	}
	
	public Caravane(String plaque, String observation, Gabarit gabari, Client client) {
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

	public Gabarit getGabarit() {
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
		if(client != null && client.getCaravane() == null) {
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
	
	
	public Camping getCurrentCamping()
	{
		if (getStatus().equals(CaravaneStatus.CAMPING)) {
			return ((EmplacementCamping)getEmplacementCamping().get(getEmplacementCamping().size() - 1)).getCamping();
		}
		return null;
	}
	  
	public EmplacementCamping getCurrentEmplacementCamping()
	{
		if (getStatus().equals(CaravaneStatus.CAMPING)) {
			return (EmplacementCamping)getEmplacementCamping().get(getEmplacementCamping().size() - 1);
		}
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
	
	public String getCSVString() {
		// Caravanes
		//Immatriculation;Gabarit;Camping/HIVERNAGE;Emplacement/Hangar;Entrée/X;Sortie/Y;-/Angle;Observation
		
		StringBuilder sb = new StringBuilder("");
		
		sb.append(getPlaque() + ";");
		
		if(getGabarit() != null) {
			sb.append(getGabarit().getNom().toString().replace("g", "") + ";");
		}
		else {
			sb.append(" ;");
		}
		
		
		switch (status) {
		
		case CAMPING: {
			sb.append(getCurrentEmplacementCamping().getCamping(). getNom() + ";");
			sb.append(getCurrentEmplacementCamping().getEmplacement() + ";");
			if(getCurrentEmplacementCamping().getEntree() != null) {
				sb.append(getCurrentEmplacementCamping().getEntree().getTime() + ";");
			}
			else {
				sb.append("null;");
			}
			if(getCurrentEmplacementCamping().getSortie() != null) {
				sb.append(getCurrentEmplacementCamping().getSortie().getTime() + ";");
			}
			else {
				sb.append("null;");
			}
			sb.append("-;");
		
		} break;
		case HANGAR: 
			sb.append("HIVERNAGE;");
			sb.append(getEmplacementHangar().getHangar().getNom() + ";");
			sb.append(getEmplacementHangar().getPosX() + ";");
			sb.append(getEmplacementHangar().getPosY() + ";");
			sb.append(getEmplacementHangar().getAngle() +"-;");

		default:
			sb.append(status.name() + ";");
			if (getEmplacementHangar() != null && getEmplacementHangar().getHangar() != null) {
				sb.append(getEmplacementHangar().getHangar().getNom() + ";");
				sb.append(getEmplacementHangar().getPosX() + ";");
				sb.append(getEmplacementHangar().getPosY() + ";");
				sb.append(getEmplacementHangar().getAngle() +"-;");
			}
		}
		sb.append(getObservation());
		
		return sb.toString();
	}
}
