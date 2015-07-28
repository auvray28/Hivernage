package com.meeple.cloud.hivernage.service;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.model.Hangar;

public interface ICaravaneService {
	
	public ArrayList<Caravane> listAll();
	
	public Caravane findById(int caravaneId);
	
	public Caravane findByPlate(String plate);
	
	public Caravane findByClientId(Client client);
	
	public void create(Caravane caravane);
	
	public void update(Caravane caravane);
	
	public void delete(Caravane caravane);
	
	public void move(Caravane caravane, Hangar hangar);
	
	public void putInHangar(Caravane caravane, Hangar hangard);
	
	public void removeFromHangar(Caravane caravane);
	
	public void putToCamping(Caravane caravane, EmplacementCamping eplacement);
	
	public void addEmplacementCamping(Caravane caravane, EmplacementCamping eplacement);
	
	
}
