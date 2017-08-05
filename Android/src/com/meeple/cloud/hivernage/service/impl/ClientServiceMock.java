package com.meeple.cloud.hivernage.service.impl;

import java.util.ArrayList;
import java.util.Iterator;

import com.meeple.cloud.hivernage.db.DBMock;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.Hivernage;
import com.meeple.cloud.hivernage.service.IClientService;
import com.meeple.cloud.hivernage.service.Services;

public class ClientServiceMock implements IClientService {

	@Override
	public ArrayList<Client> getAllClient() {
		return DBMock.DB.getClients();
	}

	@Override
	public Client findById(int clientId) {
		for(Client c : getAllClient()) {
			if(c.getClientId() == clientId) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Client findByName(String firstName, String lastName) {
		for(Client c : getAllClient()) {
			if(c.getNom().equalsIgnoreCase(lastName) && c.getPrenom().equalsIgnoreCase(firstName)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public void create(Client client) {
		client.setClientId(DBMock.DB.getNextId(Client.class));
		DBMock.DB.getClients().add(client);
		if (client.getCaravane() != null) {
			Services.caravaneService.create(client.getCaravane());
		}
	}

	@Override
	public void update(Client client) {
		Client c = findById(client.getClientId());
		c.setAdresse(client.getAdresse());
		c.setCaravane(client.getCaravane());
		c.setMail(client.getMail());
		c.setNom(client.getNom());
		c.setObservation(client.getObservation());
		c.setPrenom(client.getPrenom());
		
	}

	@Override
	public void delete(Client client) {
		Client c = findById(client.getClientId());
		DBMock.DB.getClients().remove(c);
	}

	@Override
	public void addHivernage(Client client, Hivernage hivernage) {
		findById(client.getClientId()).getHivernages().add(hivernage);
		
	}

	@Override
	public void removeHivernage(Client client, Hivernage hivernage) {
		Iterator<Hivernage> it = findById(client.getClientId()).getHivernages().iterator();
		while(it.hasNext()) {
			Hivernage h = it.next();
			if(h.getHivernageId() == hivernage.getHivernageId()) {
				it.remove();
				return;
			}
		}
	}

}
