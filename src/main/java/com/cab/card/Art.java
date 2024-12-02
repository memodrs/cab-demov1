package com.cab.card;

public enum Art {
    Mensch, Tier, Fabelwesen, Nachtgestalt, Fluch, Segen, Unbekannt;

    public String getTextbaustein() {
        switch (this) {
            case Mensch:
                return "artMensch"; 
            case Tier:
                return "artTier";
            case Fabelwesen:
                return "artFabelwesen"; 
            case Nachtgestalt:
                return "artNachtgestalt"; 
            case Fluch:
                return "artFluch";
            case Segen:
                return "artSegen"; 
            case Unbekannt:
                return "artUnbekannt"; 
            default:
                return super.toString(); 
        }
    }
}