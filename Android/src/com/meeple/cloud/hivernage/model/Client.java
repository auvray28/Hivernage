package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;

public class Client {

	private int clientId;
	private String nom;
	private String prenom;
	private String adresse;
	private String mail;
	private String observation;
	private Caravane caravane;
	private ArrayList<Hivernage> hivernages;
	
	public Client(String nom, String prenom, String adresse, String mail,
			String observation, Caravane caravane) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.mail = mail;
		this.observation = observation;
		this.caravane = caravane;
		this.hivernages = new ArrayList<Hivernage>();
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
	
	

	
}
