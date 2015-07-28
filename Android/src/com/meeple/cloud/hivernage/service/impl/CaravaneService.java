package com.meeple.cloud.hivernage.service.impl;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.service.ICaravaneService;

public class CaravaneService implements ICaravaneService {

	@Override
	public ArrayList<Caravane> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caravane findById(int caravaneId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caravane findByPlate(String plate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caravane findByClientId(Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Caravane caravane) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Caravane caravane) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Caravane caravane) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Caravane caravane, Hangar hangar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putInHangar(Caravane caravane, Hangar hangard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFromHangar(Caravane caravane) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putToCamping(Caravane caravane, EmplacementCamping eplacement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addEmplacementCamping(Caravane caravane,
			EmplacementCamping eplacement) {
		// TODO Auto-generated method stub

	}

}
