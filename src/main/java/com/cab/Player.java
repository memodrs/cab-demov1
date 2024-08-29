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
		punkte = 400;

        //XXXXXX TEST HACK STAPEL XXXXXXX
		
		System.out.println("Testversion Hack Stapel");
        stapel.add(gp.cardLoader.getCard(1).id);
		stapel.add(gp.cardLoader.getCard(3).id);
		stapel.add(gp.cardLoader.getCard(4).id);
		stapel.add(gp.cardLoader.getCard(17).id);
		stapel.add(gp.cardLoader.getCard(10).id);
		stapel.add(gp.cardLoader.getCard(11).id);
		stapel.add(gp.cardLoader.getCard(20).id);
		stapel.add(gp.cardLoader.getCard(22).id);
		stapel.add(gp.cardLoader.getCard(31).id);
		stapel.add(gp.cardLoader.getCard(40).id);
		stapel.add(gp.cardLoader.getCard(48).id);
		stapel.add(gp.cardLoader.getCard(49).id);

		stapel.add(gp.cardLoader.getCard(45).id);
		stapel.add(gp.cardLoader.getCard(76).id);
		stapel.add(gp.cardLoader.getCard(12).id);
		stapel.add(gp.cardLoader.getCard(36).id);

		stapel.add(gp.cardLoader.getCard(200).id);
		stapel.add(gp.cardLoader.getCard(201).id);
		stapel.add(gp.cardLoader.getCard(202).id);
		stapel.add(gp.cardLoader.getCard(203).id);
		stapel.add(gp.cardLoader.getCard(401).id);
    }
}
