package com.meeple.cloud.hivernage.service.impl;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.db.DBMock;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.Hivernage;
import com.meeple.cloud.hivernage.service.IHivernageService;

public class HivernageServiceMock implements IHivernageService {

	@Override
	public void createHivernage(Hivernage hivernage) {
		hivernage.setHivernageId(DBMock.DB.getNextId(Hivernage.class));
		DBMock.DB.getHivernages().add(hivernage);
		
	}

	@Override
	public void updateHivernage(Hivernage hivernage) {
		Hivernage h = findHivernageById(hivernage.getHivernageId());
		h.setAcompte(hivernage.getAcompte());
		h.setDebut(hivernage.getDebut());
		h.setFin(hivernage.getFin());
		h.setStatus(hivernage.getStatus());
	}

	@Override
	public void removeHivernage(Hivernage hivernage) {
		DBMock.DB.getHivernages().remove(hivernage);
		
	}

	@Override
	public Hivernage findHivernageById(int hivernage_id) {
		for(Hivernage h : getAllHiverages()) {
			if(h.getHivernageId() == hivernage_id) {
				return h;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Hivernage> getAllHiverages() {
		return DBMock.DB.getHivernages();
	}

	@Override
	public ArrayList<Hivernage> getAllHivernagesForClient(Client client) {
		return new ClientServiceMock().findById(client.getClientId()).getHivernages();
	}

}
