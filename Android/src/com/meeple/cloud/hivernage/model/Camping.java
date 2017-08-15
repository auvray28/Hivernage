package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;
import java.util.Comparator;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.Id;
import com.meeple.cloud.hivernage.db.annotation.OneToMany;

public class Camping extends Entity<Camping>{

	@Id
	@Column
	private int CampingId;
	@Column
	private String nom;
	@Column
	private String mail;
	@Column
	private String tel;
	@Column
	private double prix;
	@Column
	private String observations;
	
	
	@OneToMany(ref="camping", isDouble=true)
	private ArrayList<EmplacementCamping> emplacements;

	public Camping() {}
	
	public Camping(String nom, String mail, String tel, double prix, String observations) {
		this.nom = nom;
		this.mail = mail;
		this.tel = tel;
		this.prix = prix;
		this.observations = observations;
		this.emplacements = new ArrayList<EmplacementCamping>();
		
	}
	
	public Camping(String jsonObject) {
		//TODO
	}
	

	public int getCampingId() {
		return CampingId;
	}

	public void setCampingId(int campingId) {
		CampingId = campingId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public ArrayList<EmplacementCamping> getEmplacements() {
		return emplacements;
	}

	public void setEmplacements(ArrayList<EmplacementCamping> emplacements) {
		this.emplacements = emplacements;
	}
	
	
	public void addEmplacements(EmplacementCamping emplacement) {
		this.emplacements.add(emplacement);
	}
	
	
/********************* Comparators Impl�mentation ****************************/
	
	public static class Comparators {
        public static Comparator<Camping> ALPHABETIC = new Comparator<Camping>() {
            @Override
            public int compare(Camping o1, Camping o2) {
                return o1.nom.compareTo(o2.nom);
            }
        };
        
        public static Comparator<Camping> PRIX_CROISSANT = new Comparator<Camping>() {
            @Override
            public int compare(Camping o1, Camping o2) {
                return (o1.prix > (o2.prix)) ? 1 : -1;
            }
        };
      
        public static Comparator<Camping> PRIX_DECROISSANT = new Comparator<Camping>() {
            @Override
            public int compare(Camping o1, Camping o2) {
                return (o1.prix > (o2.prix)) ? -1 : 1;
            }
        };
      
        
    }

	public String getCSVString() {
		//NOM;Tel;Mail;Prix;Observation;
		return getNom() + ";" + getTel() + ";" + getMail() + ";" + getPrix() + ";" + getObservations() + "\n";
	}
	
	public String toString() {
		return getNom() + " : " + getEmplacements().size();
	}
	
}
