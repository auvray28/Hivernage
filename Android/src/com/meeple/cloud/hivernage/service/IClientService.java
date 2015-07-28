package com.meeple.cloud.hivernage.service;

import java.util.ArrayList;

import com.meeple.cloud.hivernage.model.Client;

public interface IClientService {

	public ArrayList<Client> getAllClient();
	
	public Client findById(int clientId);
	
	public Client findByName(String firstName, String lastName);
	
	public void create(Client client);
	
	public void update(Client client);
	
	public void delete(Client client);
	
}
