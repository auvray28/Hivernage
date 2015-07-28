package com.meeple.cloud.hivernage.model;

public class Tranche {

	private int trancheId;
	private int longueurMin;
	private int longueurMax;
	private double prix;
	public Tranche(int trancheId, int longueurMin, int longueurMax, double prix) {
		super();
		this.trancheId = trancheId;
		this.longueurMin = longueurMin;
		this.longueurMax = longueurMax;
		this.prix = prix;
	}
	public int getTrancheId() {
		return trancheId;
	}
	public void setTrancheId(int trancheId) {
		this.trancheId = trancheId;
	}
	public int getLongueurMin() {
		return longueurMin;
	}
	public void setLongueurMin(int longueurMin) {
		this.longueurMin = longueurMin;
	}
	public int getLongueurMax() {
		return longueurMax;
	}
	public void setLongueurMax(int longueurMax) {
		this.longueurMax = longueurMax;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	
}
