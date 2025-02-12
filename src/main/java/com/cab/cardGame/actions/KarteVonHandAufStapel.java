package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteVonHandAufStapel {
    Player p;
    int id;

    public KarteVonHandAufStapel(Player p, int id) {
        this.p = p;
        this.id = id;
    }

    public void execute(CardGame cardGame) {
		CardState card = p.handCards.get(id);
		if (cardGame.isCardInHand(card)) {
			cardGame.removeCardFromHand(p, card);
			cardGame.addCardToStapel(p, card);
			cardGame.playSE(1);
			cardGame.resolve();	
		}
    } 
}
