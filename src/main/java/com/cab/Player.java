package com.cab;

import java.util.ArrayList;
import java.util.List;


public class Player {
    GamePanel gp;
    public List<Integer> truhe = new ArrayList<Integer>();
	public List<Integer> stapel = new ArrayList<Integer>();

    public Player(GamePanel gp) {

        this.gp = gp;
        
        //XXXXXX TEST HACK TRUHE XXXXXXX
		System.out.println("Player.java TEST HACK STAPEL");
		for (int i = 0; i < gp.cardLoader.cards.size(); i++) {
			truhe.add(gp.cardLoader.cards.get(i).id);
		}

        //XXXXXX TEST HACK STAPEL XXXXXXX
        stapel.add(gp.cardLoader.getCard(4).id);
		stapel.add(gp.cardLoader.getCard(8).id);
		stapel.add(gp.cardLoader.getCard(10).id);
		stapel.add(gp.cardLoader.getCard(11).id);
		stapel.add(gp.cardLoader.getCard(12).id);
		stapel.add(gp.cardLoader.getCard(17).id);
		stapel.add(gp.cardLoader.getCard(20).id);
		stapel.add(gp.cardLoader.getCard(21).id);
		stapel.add(gp.cardLoader.getCard(31).id);
		stapel.add(gp.cardLoader.getCard(36).id);
		stapel.add(gp.cardLoader.getCard(37).id);
		stapel.add(gp.cardLoader.getCard(40).id);

		stapel.add(gp.cardLoader.getCard(45).id);
		stapel.add(gp.cardLoader.getCard(49).id);
		stapel.add(gp.cardLoader.getCard(52).id);
		stapel.add(gp.cardLoader.getCard(53).id);

		stapel.add(gp.cardLoader.getCard(200).id);
		stapel.add(gp.cardLoader.getCard(201).id);
		stapel.add(gp.cardLoader.getCard(202).id);
		stapel.add(gp.cardLoader.getCard(203).id);
		stapel.add(gp.cardLoader.getCard(401).id);
    }
}
