package com.meeple.cloud.hivernage.service.impl;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.db.DBMock;
import com.meeple.cloud.hivernage.model.Tranche;
import com.meeple.cloud.hivernage.service.ITrancheService;

public class TrancheServiceMock implements ITrancheService {

	@Override
	public void createTranche(Tranche tranche) {
		tranche.setTrancheId(DBMock.DB.getNextId(Tranche.class));
		DBMock.DB.getTranches().add(tranche);
		
	}

	@Override
	public void updateTranche(Tranche tranche) {
		Tranche t = findTrancheById(tranche.getTrancheId());
		t.setLongueurMax(tranche.getLongueurMax());
		t.setLongueurMin(tranche.getLongueurMin());
		t.setPrix(tranche.getPrix());
	}

	@Override
	public void removeTranche(Tranche tranche) {
		Tranche t = findTrancheById(tranche.getTrancheId());
		DBMock.DB.getTranches().remove(t);
	}

	@Override
	public Tranche findTrancheById(int tranche_id) {
		for(Tranche t : getAllTranches()) {
			if(t.getTrancheId() == tranche_id) {
				return t;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Tranche> getAllTranches() {
		return DBMock.DB.getTranches();
	}

	@Override
	public Tranche findTrancheByLongueur(int tranche_longueur) {
		for(Tranche t : getAllTranches()) {
			if(t.getLongueurMin() <= tranche_longueur && tranche_longueur < t.getLongueurMax()) {
				return t;
			}
		}
		return null;
	}


}
