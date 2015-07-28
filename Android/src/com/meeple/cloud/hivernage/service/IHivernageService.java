package com.meeple.cloud.hivernage.service;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.Hivernage;

public interface IHivernageService {

	public void createHivernage(Hivernage hivernage);
	public void updateHivernage(Hivernage hivernage);
	public void removeHivernage(Hivernage hivernage);
	
	public Hivernage findHivernageById(int hivernage_id);

	public ArrayList<Hivernage> getAllHiverages();
	public ArrayList<Hivernage> getAllHivernagesForClient(Client client);

	
	
	
}
