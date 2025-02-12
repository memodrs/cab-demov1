package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteVonStapelAufHand {
    
    Player p;
    int id;

    public KarteVonStapelAufHand(Player p, int id) {
        this.p = p;
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);
		if (cardGame.isCardInStapel(card)) {
			cardGame.removeCardFromStapel(p, card);
			cardGame.addCardToHand(p, card, true);
			cardGame.resolve();
		}
    }
    
}
