package com.meeple.cloud.hivernage.model;

import java.util.ArrayList;
import java.util.Comparator;

import com.meeple.cloud.hivernage.db.annotation.Column;
import com.meeple.cloud.hivernage.db.annotation.GsonTransient;
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
	private String contact_nom_1;
	@Column
	private String contact_nom_2;
	@Column
	private String contact_tel_1;
	@Column
	private String contact_tel_2;
	@Column
	private double prix;
	@Column
	private String observations;
	
	@GsonTransient
	@OneToMany(ref="camping", isDouble=true)
	private ArrayList<EmplacementCamping> emplacements;

	public Camping() {
		// car @GSonTransient, donc n'est pas recreer lors lecture
		this.emplacements = new ArrayList<EmplacementCamping>();
	}
	
	public Camping(String nom, String mail, String tel, String contact_nom1, String contact_tel1, String contact_nom2, String contact_tel2, double prix, String observations) {
		this.nom = nom;
		this.mail = mail;
		this.tel = tel;
		this.contact_nom_1 = contact_nom1;
		this.contact_tel_1 = contact_tel1;
		this.contact_nom_2 = contact_nom2;
		this.contact_tel_2 = contact_tel2;
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
	
	public String getContact_nom_1() {
		return contact_nom_1;
	}

	public void setContact_nom_1(String contact_nom_1) {
		this.contact_nom_1 = contact_nom_1;
	}

	public String getContact_nom_2() {
		return contact_nom_2;
	}

	public void setContact_nom_2(String contact_nom_2) {
		this.contact_nom_2 = contact_nom_2;
	}

	public String getContact_tel_1() {
		return contact_tel_1;
	}

	public void setContact_tel_1(String contact_tel_1) {
		this.contact_tel_1 = contact_tel_1;
	}

	public String getContact_tel_2() {
		return contact_tel_2;
	}

	public void setContact_tel_2(String contact_tel_2) {
		this.contact_tel_2 = contact_tel_2;
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
		//NOM;Tel;Contact_1;Contact_tel_1; Contact_2;Contact_tel_2;Mail;Prix;Observation;
		return  getNom() + ";" + getTel() + ";" + getContact_nom_1() + ";" + getContact_tel_1() 
				+ ";" +getContact_nom_2()+ ";" + getContact_tel_2() + ";"  + getMail() + ";" 
				+ getPrix() + ";" + getObservations() + "\n";
	}
	
	public String toString() {
		return getNom() + " : " + getEmplacements().size();
	}
	
}
