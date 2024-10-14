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
    }
}
