package com.cab.save;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveModel {
    public int punkte;
    public Integer[] stapel;
    public Integer[] truhe;

    public Map<String, List<Integer>> savedStapel = new HashMap<>();

    // Jackson benötigt diesen Konstruktor, um das Objekt zu deserialisieren
    public SaveModel() {}

    public SaveModel(int punkte, Integer[] stapel, Integer[] truhe, Map<String, List<Integer>> savedStapel) {
        this.punkte = punkte;
        this.stapel = stapel;
        this.truhe = truhe;
        this.savedStapel = savedStapel;
    }
}
