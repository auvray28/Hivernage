package com.meeple.cloud.hivernage.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.meeple.cloud.hivernage.db.file.FileManager;
import com.meeple.cloud.hivernage.model.Camping;
import com.meeple.cloud.hivernage.model.Caravane;
import com.meeple.cloud.hivernage.model.Client;
import com.meeple.cloud.hivernage.model.EmplacementCamping;
import com.meeple.cloud.hivernage.model.EmplacementHangar;
import com.meeple.cloud.hivernage.model.Facture;
import com.meeple.cloud.hivernage.model.Gabarit;
import com.meeple.cloud.hivernage.model.Hangar;
import com.meeple.cloud.hivernage.model.Hivernage;
import com.meeple.cloud.hivernage.model.Tranche;
import com.meeple.cloud.hivernage.model.enums.HivernageStatus;
import com.meeple.cloud.hivernage.service.Services;

import android.content.Context;

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
	
	private HashMap<String, Integer> lastIds;
	
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

		lastIds = new HashMap<String, Integer>();

//		fill();
	}
	
	
	
	public void createDefaultObject() {
		DbHelper.instance.isImportingModel = true;
		
		// Gabarit par defaut
	    Services.gabaritService.createGabarit(new Gabarit("g1", 250, 170));
	    Services.gabaritService.createGabarit(new Gabarit("g2", 300, 170));
	    Services.gabaritService.createGabarit(new Gabarit("g3", 400, 170));
	    Services.gabaritService.createGabarit(new Gabarit("g4", 500, 170));
	    Services.gabaritService.createGabarit(new Gabarit("g5", 600, 170));
	    // Tranche par defaut
	    Services.trancheService.createTranche(new Tranche(1, 100, 260, 100.0D));
	    Services.trancheService.createTranche(new Tranche(2, 261, 310, 200.0D));
	    Services.trancheService.createTranche(new Tranche(3, 311, 1000, 400.0D));
	    // hangar par defaut
	    Services.hangarService.createHangar(new Hangar("Porcherie", 0, 0));
	    //Services.hangarService.createHangar(new Hangar("Lavage", 1, 1));
	    Services.hangarService.createHangar(new Hangar("Waiting", 1, 1));
	    
	    DbHelper.instance.isImportingModel = false;
	}
	
	public void fill() {
		Services.gabaritService.createGabarit(new Gabarit("g1", 250, 170));
		Services.gabaritService.createGabarit(new Gabarit("g2", 300, 170));
		Services.gabaritService.createGabarit(new Gabarit("g3", 400, 170));
		
		Services.trancheService.createTranche(new Tranche(1, 100, 260, 100));
		Services.trancheService.createTranche(new Tranche(2, 261, 310, 200));
		Services.trancheService.createTranche(new Tranche(3, 311, 1000, 400));
		
		Caravane caravane = new Caravane("Renault", "AB213CD", "un peu grosse", gabarits.get(0), null);
		Client c1 = new Client("Dupont", "Pierre", "il habite la", "0681623914", "ducon@gmail.com", "c'est un autiste", caravane);
		c1.addHivernage(new Hivernage(HivernageStatus.IMPAYE, -250));
		caravane.setClient(c1);
		Services.clientService.create(c1);

		Caravane caravane2 = new Caravane("Renault", "GB852AD", "un peu grosse", gabarits.get(1), null);
		Client c2 = new Client("LeBolosse", "Jacky", "il habite la aussi", "0452316985","connard@gmail.com", "c'est un autiste", caravane2);
		c2.addHivernage(new Hivernage(HivernageStatus.ACCOMPTE, 250));
		caravane2.setClient(c2);

		Services.clientService.create(c2);
		
		Caravane caravane3 = new Caravane("Renault", "AF964SF", "WTF", gabarits.get(1), null);
		Client c3 = new Client("Morane", "Bob", "1 rue des la corniche, 06560 Valbonne", "07666777","bob_morane@gmail.com", "le heros de tous les temps", caravane3);
		c3.addHivernage(new Hivernage(HivernageStatus.IMPAYE, -159));
		caravane3.setClient(c3);
		Services.clientService.create(c3);
		
		Caravane caravane4 = new Caravane("Renault", "9247VM28", "Mine!", gabarits.get(2), null);
		Client c4 = new Client("Auvray", "François", "50 rue François Lépine, 28600 Luisant", "0237341425","auvray28@gmail.com", "Coucou!", caravane4);
		c4.addHivernage(new Hivernage(HivernageStatus.PAYE, 0));
		caravane4.setClient(c4);
		Services.clientService.create(c4);
		
		Caravane caravane5 = new Caravane("Renault", "000VM28", "Mine!", gabarits.get(2), null);
		Client c5 = new Client("nom", "prenom", "adresse", "0237341425","mail", "Coucou!", caravane5);
		c5.addHivernage(new Hivernage(HivernageStatus.PAYE, 0));
		caravane5.setClient(c5);
		Services.clientService.create(c5);
		
		
		Services.hangarService.createHangar(new Hangar("Porcherie", 500, 200));
		Services.hangarService.createHangar(new Hangar("hangar 2", 20, 20));
		Services.hangarService.createHangar(new Hangar("hangar 3", 40, 40));
		Services.hangarService.createHangar(new Hangar("hangar 4", 30, 30));
		
		Hangar porch = Services.hangarService.findHangarByName("Porcherie");
		
		EmplacementHangar porch1 = new EmplacementHangar(10, 10, 0,  porch);
		Services.caravaneService.putInHangar(caravane, porch1);

		EmplacementHangar porch2 = new EmplacementHangar(120, 10, 0,  porch);
		Services.caravaneService.putInHangar(caravane2, porch2);

		EmplacementHangar porch3 = new EmplacementHangar(230, 10, 0,  porch);
		Services.caravaneService.putInHangar(caravane3, porch3);

//		EmplacementHangar porch4 = new EmplacementHangar(10, 110, 0,  porch);
//		Services.caravaneService.putInHangar(caravane4, porch4);

		
		//Services.hangarService.createHangar(new Hangar("Lavage", 1, 1));
		Services.hangarService.createHangar(new Hangar("Waiting", 1, 1));
		
//		Hangar lavage = Services.hangarService.findHangarByName("Lavage");
		
//		EmplacementHangar empLavage = new EmplacementHangar(10, 110, 0,  lavage);
//		Services.caravaneService.putInHangar(caravane5, empLavage);
		
		Hangar waiting = Services.hangarService.findHangarByName("Waiting");
		
		EmplacementHangar empWaiting = new EmplacementHangar(10, 110, 0,  waiting);
		Services.caravaneService.putInHangar(caravane4, empWaiting);
		
		
		/*********Campings**************/
		
		Caravane caravane6 = createCaravane(6);
		Caravane caravane7 = createCaravane(7);
		Caravane caravane8 = createCaravane(8);
		Caravane caravane9 = createCaravane(9);
		
		Camping ca = new Camping("Antibes", "antibes@gmail.com", "0102030405", 2000, "observations");
		
		Date d = new Date(2016,1,31);
		Date d2 = new Date(2016,2,24);
		
		EmplacementCamping em1 = new EmplacementCamping(ca, "Antibes_1", caravane6);em1.setEntree(d); em1.setSortie(d2);
		EmplacementCamping em2 = new EmplacementCamping(ca, "Antibes_2", caravane7);em2.setEntree(d); em2.setSortie(d2);
		EmplacementCamping em3 = new EmplacementCamping(ca, "Antibes_3", caravane8);em3.setEntree(d); em3.setSortie(d2);
		EmplacementCamping em4 = new EmplacementCamping(ca, "Antibes_4", caravane9);em4.setEntree(d); em4.setSortie(d2);
		
		ArrayList<EmplacementCamping> alec = new ArrayList<EmplacementCamping>();
		alec.add(em1);alec.add(em2);alec.add(em3);alec.add(em4);
		ca.setEmplacements(alec);
		
		Services.campingService.createCamping(ca);
		Services.campingService.createCamping(new Camping("Chartres", "chartres@gmail.com", "0237341425", 1000, "observations1"));
		Services.campingService.createCamping(new Camping("Paris", "paris@gmail.com", "0681623914", 500, "observations2"));
		Services.campingService.createCamping(new Camping("Nice", "nice@gmail.com", "0985647526", 100, "observations3"));
		

	}
	
	public Caravane createCaravane(int i){
		Caravane caravaneX = new Caravane("Renault", "00"+i+"VM28", "Mine!", gabarits.get(2), null);
		Client cX = new Client("nom_"+i, "prenom_"+i, "adresse_"+i, "0237341425","mail", "Coucou!", caravaneX);
		cX.addHivernage(new Hivernage(HivernageStatus.PAYE, i));
		caravaneX.setClient(cX);
		Services.clientService.create(cX);
		
		return caravaneX;
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
	
	public int getNextId(String c){
		if(! lastIds.containsKey(c)) {
			lastIds.put(c, 0);
			return 0;
		}
		Integer lastId = lastIds.get(c);
		int nextId = lastId + 1;
		lastIds.put(c, nextId);
		return nextId;
	}
	
	public HashMap<String, Integer> getLastIds() {
		return this.lastIds;
	}
	
	public ArrayList<Caravane> getCaravanes() {
		return caravanes;
	}
	
	public void setLastIds(HashMap<String, Integer> paramHashMap) 	{
		this.lastIds = paramHashMap;
	}
	
	public String getLastIdsString() {
		return getLastIds().toString().replace("=", ":");
	}
	public HashMap<String, Integer> getLastIdsForJSon() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for ( String keyClass : this.lastIds.keySet()) {
			map.put(keyClass, this.lastIds.get(keyClass));
		}
		
		return map;
	}
}
