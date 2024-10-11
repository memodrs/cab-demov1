package com.cab;

import java.util.ArrayList;
import java.util.List;


public class Player {
    GamePanel gp;
	public int punkte = 0;
	public List<Integer> newCardIds = new ArrayList<Integer>();
    public List<Integer> truhe = new ArrayList<Integer>();
	public List<Integer> stapel = new ArrayList<Integer>();

    public Player(GamePanel gp) {

        this.gp = gp;

		//XXXXXX TEST HACK PUNKTE XXXXXXX
		System.out.println("Testversion Hack Punkte");
		punkte = 4000;

		//XXXXXX TEST HACK TRUHE XXXXXXX
		System.out.println("Testversion Hack Stapel");
		for (int i = 0; i < 138; i++) {
			truhe.add(i);
		}
    }
}
