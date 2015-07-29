package com.meeple.cloud.hivernage.service.impl;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.db.DBMock;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.service.IGabaritService;

public class GabaritServiceMock implements IGabaritService {

	@Override
	public void createGabarit(Gabarit gabarit) {
		gabarit.setGabarittttttttttttttttId(DBMock.DB.getNextId(Gabarit.class));
		DBMock.DB.getGabarits().add(gabarit);
	}

	@Override
	public void updateGabarit(Gabarit gabarit) {
		Gabarit g = findGabaritById(gabarit.getGabarittttttttttttttttId());
		g.setLargeur(gabarit.getLargeur());
		g.setLongueur(gabarit.getLongueur());
		g.setNom(gabarit.getNom());
	}

	@Override
	public void removeGabarit(Gabarit gabarit) {
		Gabarit g = findGabaritById(gabarit.getGabarittttttttttttttttId());
		DBMock.DB.getGabarits().remove(g);
	}

	@Override
	public Gabarit findGabaritById(int gabaritId) {
		for(Gabarit g : getAllGabarits()) {
			if(g.getGabarittttttttttttttttId() == gabaritId) {
				return g;
			}
		}
		return null;
	}

	@Override
	public Gabarit findGabaritByName(String gabaritName) {
		for(Gabarit g : getAllGabarits()) {
			if(g.getNom().equalsIgnoreCase(gabaritName)) {
				return g;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Gabarit> getAllGabarits() {
		
		return DBMock.DB.getGabarits();
	}

}
