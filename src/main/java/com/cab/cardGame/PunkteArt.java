package com.cab.cardGame;

public enum PunkteArt {
	Leben, Fluch, Segen;

	@Override
	public String toString() {
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

