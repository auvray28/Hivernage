package com.meeple.cloud.hivernage.service.impl;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.db.DBMock;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.service.ICampingService;

public class CampingServiceMock implements ICampingService {

	@Override
	public void createCamping(Camping camping) {
		camping.setCampingId(DBMock.DB.getNextId(Camping.class));
		DBMock.DB.getCampings().add(camping);
	}

	@Override
	public void updateCamping(Camping camping) {
		Camping c = findCampingById(camping.getCampingId());
		c.setMail(camping.getMail());
		c.setNom(camping.getNom());
		c.setPrix(camping.getPrix());
		c.setTel(camping.getTel());
	}

	@Override
	public void removeCamping(Camping camping) {
		Camping c = findCampingById(camping.getCampingId());
		DBMock.DB.getCampings().remove(c);
	}

	@Override
	public Camping findCampingById(int camping_id) {
		for(Camping c : getAllCampings()) {
			if( c.getCampingId() == camping_id) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Camping findCampingByName(String campingName) {
		for(Camping c : getAllCampings()) {
			if( c.getNom().equals(campingName)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Camping> getAllCampings() {
		return DBMock.DB.getCampings();
	}

}
