package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;
import java.util.Comparator;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.db.annotation.OneToMany;

public class Client extends Entity<Client>{

	@Id
	@Column
	private int clientId;
	@Column
	private String nom;
	@Column
	private String prenom;
	@Column
	private String adresse;
	@Column
	private String telephone;
	@Column
	private String mail;
	@Column
	private String observation;
	
	@Column(colName="CARAVANE_ID")
	private Caravane caravane;
	
	@OneToMany(colName="CLIENT_ID")
	private ArrayList<Hivernage> hivernages;
	
	public Client() {}
	
	public Client(String nom, String prenom, String adresse, String telephone, String mail,
			String observation, Caravane caravane) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.telephone = telephone;
		this.mail = mail;
		this.observation = observation;
		this.caravane = caravane;
		this.hivernages = new ArrayList<Hivernage>();
		
		if(caravane.getClient() == null) {
			this.caravane.setClient(this);
		}
	}
	

	public Client(String jsonString) {
		//TODO
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Caravane getCaravane() {
		return caravane;
	}

	public void setCaravane(Caravane caravane) {
		this.caravane = caravane;
		if(caravane.getClient() == null) {
			this.caravane.setClient(this);
		}
	}

	public ArrayList<Hivernage> getHivernages() {
		return hivernages;
	}
	
	public void addHivernage(Hivernage newHivernage) {
		hivernages.add(newHivernage);
	}
	
	public int getCurrentAcompte() {
		int currentAcompte = 0;
		
		for (Hivernage h : getHivernages()) {
			currentAcompte += h.getAcompte();
		}
		return currentAcompte;
	}
	
	
	/********************* Comparators Implémentation ****************************/
	
	public static class Comparators {
        public static Comparator<Client> ALPHABETIC = new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                int name_result = o1.nom.compareTo(o2.nom);
                
                if (name_result == 0) return o1.prenom.compareTo(o2.prenom);
                else 				  return name_result;
            }
        };
        public static Comparator<Client> RESTEDU = new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                return o1.getCurrentAcompte() - o2.getCurrentAcompte();
            }
        };
        public static Comparator<Client> CAMPING = new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
            	
            	if (o1.caravane.getCurrentCamping() == null && o2.caravane.getCurrentCamping() == null) return 0; 
            	if (o2.caravane.getCurrentCamping() == null) return  1; 
            	if (o1.caravane.getCurrentCamping() == null) return -1; 
            	
                return o1.caravane.getCurrentCamping().getNom().compareTo(o2.caravane.getCurrentCamping().getNom());
            }
        };
        
        public static Comparator<Client> HANGAR = new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
            	
            	if (o1.caravane.getEmplacementHangar() == null && o2.caravane.getEmplacementHangar() == null) return 0; 
            	if (o2.caravane.getEmplacementHangar() == null) return  1; 
            	if (o1.caravane.getEmplacementHangar() == null) return -1; 
            	
                return o1.caravane.getEmplacementHangar().getHangar().getNom().compareTo(o2.caravane.getEmplacementHangar().getHangar().getNom());
            }
        };
    }


	public CharSequence getFullName() {
		return nom + " " + prenom; 
	}	
}
