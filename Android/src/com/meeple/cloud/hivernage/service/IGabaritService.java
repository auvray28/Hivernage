package com.meeple.cloud.hivernage.service;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Gabarit;

public interface IGabaritService {

	public void createGabarit(Gabarit gabarit);
	public void updateGabarit(Gabarit gabarit);
	public void removeGabarit(Gabarit gabarit);
	
	public Gabarit findGabaritById(int gabarit_id);
	public Gabarit findGabaritByName(String gabarit_name);
	
	public ArrayList<Gabarit> getAllGabarits();
	
	
	
}
