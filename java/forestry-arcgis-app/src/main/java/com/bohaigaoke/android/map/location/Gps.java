package com.bohaigaoke.android.map.location;

public class Gps {
	private double mgLat;
	private double mgLon;
	public Gps(double mgLat, double mgLon) {
		this.mgLat = mgLat;
		this.mgLon = mgLon;
	}

	public double getWgLon() {
		return mgLon;
	}
	public double getWgLat() {
		return mgLat;
	}

}
