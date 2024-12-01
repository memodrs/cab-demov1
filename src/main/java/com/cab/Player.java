package com.cab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Player {
    GamePanel gp;
	public int punkte = 0;
	public List<Integer> newCardIds = new ArrayList<Integer>();
    public List<Integer> truhe = new ArrayList<Integer>();
	public List<Integer> stapel = new ArrayList<Integer>();

    public Map<String, List<Integer>> savedStapel = new HashMap<>();
    
    public Player(GamePanel gp) {
        this.gp = gp;
    }
}
