package com.meeple.cloud.hivernage.service;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Tranche;

public interface ITrancheService {

	public void createTranche(Tranche tranche);
	public void updateTranche(Tranche tranche);
	public void removeTranche(Tranche tranche);
	
	public Tranche findTrancheById(int tranche_id);
	public Tranche findTrancheByLongueur(int tranche_longueur);
	
	public ArrayList<Tranche> getAllTranches();
}
