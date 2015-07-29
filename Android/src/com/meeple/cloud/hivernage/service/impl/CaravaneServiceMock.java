package com.meeple.cloud.hivernage.service.impl;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.db.DBMock;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.model.enums.CaravaneStatus;
import com.meeple.cloud.hivernage.service.ICaravaneService;

public class CaravaneServiceMock implements ICaravaneService {

	@Override
	public ArrayList<Caravane> listAll() {
		return DBMock.DB.getCaravanes();
	}

	@Override
	public Caravane findById(int caravaneId) {
		for(Caravane c : listAll()) {
			if(c.getCaravaneId() == caravaneId) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Caravane findByPlate(String plate) {
		for(Caravane c : listAll()) {
			if(c.getPlaque().equalsIgnoreCase(plate)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Caravane findByClientId(Client client) {
		for(Caravane c : listAll()) {
			if(c.getClient().getClientId() == client.getClientId()) {
				return c;
			}
		}
		return null;
	}

	@Override
	public void create(Caravane caravane) {
		caravane.setCaravaneId(DBMock.DB.getNextId(Caravane.class));
		DBMock.DB.getCaravanes().add(caravane);

	}

	@Override
	public void update(Caravane caravane) {
		Caravane c = findById(caravane.getCaravaneId());
		c.setAngle(caravane.getAngle());
		c.setClient(caravane.getClient());
		c.setCoord(caravane.getCoord());
		c.setGabari(caravane.getGabari());
		c.setHangar(caravane.getHangar());
		c.setObservation(caravane.getObservation());
		c.setPlaque(caravane.getPlaque());
		c.setStatus(caravane.getStatus());
	}

	@Override
	public void delete(Caravane caravane) {
		Caravane c = findById(caravane.getCaravaneId());
		DBMock.DB.getCaravanes().remove(c);
	}

	@Override
	public void move(Caravane caravane, Hangar hangar) {
		removeFromHangar(caravane);
		putInHangar(caravane, hangar);
	}

	@Override
	public void putInHangar(Caravane caravane, Hangar hangar) {
		hangar.getCaravanes().add(caravane);
		caravane.setHangar(hangar);
		caravane.setStatus(CaravaneStatus.HANGAR);
	}

	@Override
	public void removeFromHangar(Caravane caravane) {
		caravane.getHangar().removeCaravane(caravane);
		caravane.setHangar(null);
	}

	@Override
	public void putToCamping(Caravane caravane, EmplacementCamping emplacement) {
		caravane.setStatus(CaravaneStatus.CAMPING);
	}

	@Override
	public void addEmplacementCamping(Caravane caravane,
			EmplacementCamping emplacement) {
		caravane.getEmplacementCamping().add(emplacement);
		DBMock.DB.getEmplacements().add(emplacement);
	}

	@Override
	public void removeFromCamping(Caravane caravane) {
		//TODO
		caravane.setStatus(CaravaneStatus.SORTIE);
	}

}
