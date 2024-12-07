package com.cab.save;

import java.util.ArrayList;
import java.util.List;

import com.cab.configs.Sprache;

import lombok.Getter;

@Getter
public class SaveModel {
    private int punkte;
    private Integer[] stapel;
    private Integer[] truhe;
    private Sprache sprache;

	public List<List<Integer>> savedStapel = new ArrayList<List<Integer>>();

    // Jackson benötigt diesen Konstruktor, um das Objekt zu deserialisieren
    public SaveModel() {}

    public SaveModel(int punkte, Integer[] stapel, Integer[] truhe, List<List<Integer>> savedStapel, Sprache sprache) {
        this.punkte = punkte;
        this.stapel = stapel;
        this.truhe = truhe;
        this.savedStapel = savedStapel;
        this.sprache = sprache;
    }
}
