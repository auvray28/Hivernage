package com.meeple.cloud.hivernage.service.impl;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.db.DBMock;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.service.IHangarService;


public class HangarServiceMock implements IHangarService {

	@Override
	public void createHangar(Hangar hangar) {
		hangar.setHangarId(DBMock.DB.getNextId(Hangar.class));
		DBMock.DB.getHangars().add(hangar);
	}

	@Override
	public void updateHangar(Hangar hangar) {
		Hangar h = findHangarById(hangar.getHangarId());
		h.setLargeur(hangar.getLargeur());
		h.setLongueur(hangar.getLongueur());
		h.setNom(hangar.getNom());
	}

	@Override
	public void removeHangar(Hangar hangar) {
		Hangar h = findHangarById(hangar.getHangarId());
		DBMock.DB.getHangars().remove(h);
	}

	@Override
	public Hangar findHangarById(int hangarId) {
		for(Hangar h : getAllHangars()) {
			if(h.getHangarId() == hangarId) {
				return h;
			}
		}
		return null;
	}

	@Override
	public Hangar findHangarByName(String hangarName) {
		for(Hangar h : getAllHangars()) {
			if(h.getNom().equalsIgnoreCase(hangarName)) {
				return h;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Hangar> getAllHangars() {
		return DBMock.DB.getHangars();
	}

}
