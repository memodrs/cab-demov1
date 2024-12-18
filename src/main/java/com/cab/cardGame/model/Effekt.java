package com.cab.cardGame.model;

public class Effekt {
    public Player p;
    public int id;
    public int trigger; 
    public int idArgForEffekt; 

    public Effekt(Player p, int id, int trigger, int idArgForEffekt) {
        this.p = p;
        this.id = id;
        this.trigger = trigger;
        this.idArgForEffekt = idArgForEffekt;
    }
}
