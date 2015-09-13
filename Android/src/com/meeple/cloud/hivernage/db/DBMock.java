package com.meeple.cloud.hivernage.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.location.Address;

import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.model.Facture;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.model.Hivernage;
import com.meeple.cloud.hivernage.model.Tranche;
import com.meeple.cloud.hivernage.model.enums.HivernageStatus;
import com.meeple.cloud.hivernage.service.Services;

public class DBMock {
	
	public static final DBMock DB = new DBMock();

	private ArrayList<Client> clients;
	
	public ArrayList<Tranche> tranches;
	
	private ArrayList<Gabarit> gabarits;
	
	private ArrayList<Hangar> hangars;
	
	private ArrayList<Hivernage> hivernages;
	
	private ArrayList<EmplacementCamping> emplacements;
	
	private ArrayList<Camping> campings;
	
	private ArrayList<Facture> factures;
	
	private HashMap<Class<? extends Object>, Integer> lastIds;
	
	private ArrayList<Caravane> caravanes;
	
	public DBMock() {
		init();
	}
	public void init() {
		
		campings = new ArrayList<Camping>();
		caravanes = new ArrayList<Caravane>();
		clients = new ArrayList<Client>();
		gabarits = new ArrayList<Gabarit>();
		hangars = new ArrayList<Hangar>();
		hivernages = new ArrayList<Hivernage>();
		emplacements = new ArrayList<EmplacementCamping>();
		campings = new ArrayList<Camping>();
		factures = new ArrayList<Facture>();
		tranches = new ArrayList<Tranche>();

		lastIds = new HashMap<Class<? extends Object>, Integer>();

//		fill();
	}
	
	public void fill() {
		Services.gabaritService.createGabarit(new Gabarit("g1", 250, 170));
		Services.gabaritService.createGabarit(new Gabarit("g2", 300, 170));
		Services.gabaritService.createGabarit(new Gabarit("g3", 400, 170));
		
		Services.trancheService.createTranche(new Tranche(1, 100, 260, 100));
		Services.trancheService.createTranche(new Tranche(2, 261, 310, 200));
		Services.trancheService.createTranche(new Tranche(3, 311, 1000, 400));
		
		Caravane caravane = new Caravane("AB213CD", "un peu grosse", gabarits.get(2), null);
		Client c1 = new Client("Dupont", "Pierre", "il habite la", "ducon@gmail.com", "c'est un autiste", caravane);
		c1.addHivernage(new Hivernage(HivernageStatus.IMPAYE, -250));
		caravane.setClient(c1);
		Services.clientService.create(c1);

		Caravane caravane2 = new Caravane("GB852AD", "un peu grosse", gabarits.get(1), null);
		Client c2 = new Client("LeBolosse", "Jacky", "il habite la aussi", "connard@gmail.com", "c'est un autiste", caravane2);
		c2.addHivernage(new Hivernage(HivernageStatus.ACCOMPTE, 250));
		caravane2.setClient(c2);

		Services.clientService.create(c2);
		
		Caravane caravane3 = new Caravane("AF964SF", "WTF", gabarits.get(1), null);
		Client c3 = new Client("Morane", "Bob", "1 rue des la corniche, 06560 Valbonne", "bob_morane@gmail.com", "le heros de tous les temps", caravane3);
		c3.addHivernage(new Hivernage(HivernageStatus.IMPAYE, -159));
		caravane3.setClient(c3);
		Services.clientService.create(c3);
		
		Caravane caravane4 = new Caravane("9247VM28", "Mine!", gabarits.get(2), null);
		Client c4 = new Client("Auvray", "François", "50 rue François Lépine, 28600 Luisant", "auvray@gmail.com", "Coucou!", caravane4);
		c1.addHivernage(new Hivernage(HivernageStatus.PAYE, 0));
		caravane4.setClient(c4);
		Services.clientService.create(c4);
		
		
		Services.hangarService.createHangar(new Hangar("hangar 1", 50, 20));
		Services.hangarService.createHangar(new Hangar("hangar 2", 20, 20));
		Services.hangarService.createHangar(new Hangar("hangar 3", 40, 40));
		Services.hangarService.createHangar(new Hangar("hangar 4", 30, 30));

	}
	
	public ArrayList<Client> getClients() {
		return clients;
	}
	public ArrayList<Tranche> getTranches() {
		return tranches;
	}
	public ArrayList<Gabarit> getGabarits() {
		return gabarits;
	}
	public ArrayList<Hangar> getHangars() {
		return hangars;
	}
	public ArrayList<Hivernage> getHivernages() {
		return hivernages;
	}
	public ArrayList<EmplacementCamping> getEmplacements() {
		return emplacements;
	}
	public ArrayList<Camping> getCampings() {
		return campings;
	}
	public ArrayList<Facture> getFactures() {
		return factures;
	}
	
	public int getNextId(Class<? extends Object> c){
		if(! lastIds.containsKey(c)) {
			lastIds.put(c, 0);
			return 0;
		}
		Integer lastId = lastIds.get(c);
		int nextId = lastId + 1;
		lastIds.put(c, nextId);
		return nextId;
	}
	public ArrayList<Caravane> getCaravanes() {
		return caravanes;
	}
}
