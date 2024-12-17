package com.cab.cardGame.config;

public enum PunkteArt {
	Leben, Fluch, Segen;

	public String getTextbaustein() {
		switch (this) {
			case Leben:
				return "punkteArtLeben"; 
			case Fluch:
				return "punkteArtFluch"; 
			case Segen:
				return "punkteArtSegen"; 
			default:
				return super.toString(); 
		}
	}
}

