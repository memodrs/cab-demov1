package com.cab.cardGame;

public class Effekt {
    Player p;
    int id;
    int trigger; 
    int idArgForEffekt; // durch id ersetzen

    public Effekt(Player p, int id, int trigger, int idArgForEffekt) {
        this.p = p;
        this.id = id;
        this.trigger = trigger;
        this.idArgForEffekt = idArgForEffekt;
    }
}
