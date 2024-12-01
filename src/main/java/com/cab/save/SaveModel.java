package com.cab.save;

import java.util.ArrayList;
import java.util.List;

public class SaveModel {
    public int punkte;
    public Integer[] stapel;
    public Integer[] truhe;

	public List<List<Integer>> savedStapel = new ArrayList<List<Integer>>();

    // Jackson benötigt diesen Konstruktor, um das Objekt zu deserialisieren
    public SaveModel() {}

    public SaveModel(int punkte, Integer[] stapel, Integer[] truhe, List<List<Integer>> savedStapel) {
        this.punkte = punkte;
        this.stapel = stapel;
        this.truhe = truhe;
        this.savedStapel = savedStapel;
    }
}
