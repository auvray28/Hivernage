package com.meeple.cloud.hivernage.service;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Tranche;

public interface ITrancheService {

	public void createTranche(Tranche tranche);
	public void updateTranche(Tranche tranche);
	public void removeTranche(Tranche tranche);
	
	public Camping findTrancheById(int tranche_id);
	public Camping findTrancheByName(String tranche_name);
	
	public ArrayList<Tranche> getAllTranches();


}
