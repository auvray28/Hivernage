package com.meeple.cloud.hivernage.service;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.Hivernage;

public interface IClientService {

	public ArrayList<Client> getAllClient();
	
	public Client findById(int clientId);
	
	public Client findByName(String firstName, String lastName);
	
	public void create(Client client);
	
	public void update(Client client);
	
	public void delete(Client client);
	
	public void addHivernage(Client client, Hivernage hivernage);

	public void removeHivernage(Client client, Hivernage hivernage);
}
