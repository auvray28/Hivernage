package com.meeple.cloud.hivernage.service;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Hangar;

public interface IHangarService {

	public void createHangar(Hangar hangar);
	public void updateHangar(Hangar hangar);
	public void removeHangar(Hangar hangar);
	
	public Hangar findHangarById(int hangar_id);
	public Hangar findHangarByName(String hangar_name);
	
	public ArrayList<Hangar> getAllHangar();
	

	
}
