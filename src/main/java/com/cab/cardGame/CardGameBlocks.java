package com.cab.cardGame;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class CardGameBlocks {
    private Map<CardState, Player> blockCardsOnBoard = new LinkedHashMap<>();
    private Player player;
    private Player oponent;

    public CardGameBlocks(Player player, Player oponent){
        this.player = player;
        this.oponent = oponent;
    }
    
    public void putCard(Player p, CardState card) {
		blockCardsOnBoard.put(card, p);
		updateBoardBlocks();
	}

	public void removeCard(Player p, CardState card) {
		blockCardsOnBoard.remove(card, p);
		updateBoardBlocks();
	}

	private void updateBoardBlocks() {
		player.resetBlocks();
		oponent.resetBlocks();

        for (Map.Entry<CardState, Player> entry : blockCardsOnBoard.entrySet()) {
            Player owner = entry.getValue();
            if (!owner.isEffektBlockiert(entry.getKey())) {
                entry.getKey().setBlock(owner, owner.isPlayer? oponent : player);
            }
        }
	}
}
