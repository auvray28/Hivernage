package com.meeple.cloud.hivernage.service;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Camping;

public interface ICampingService {

	public void createCamping(Camping camping);
	public void updateCamping(Camping camping);
	public void removeCamping(Camping camping);
	
	public Camping findCampingById(int camping_id);
	public Camping findCampingByName(String camping_name);
	
	public ArrayList<Camping> getAllCamping();


}
