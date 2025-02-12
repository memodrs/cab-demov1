package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KartenZiehen {

    Player p;
    int numberOfCards;

    public KartenZiehen(Player p, int numberOfCards) {
        this.p = p;
        this.numberOfCards = numberOfCards;
    }

    public void execute(CardGame cardGame) {
        for (int i = 0; i < numberOfCards; i++) {
			if (p.stapel.isEmpty() || p.handCards.size() >= cardGame.limitCardsInHand) {
				break;
			}
	
			int index = p.stapel.size() - 1;
			CardState card = p.stapel.get(index);

			if (cardGame.isCardInStapel(card)) {
				cardGame.removeCardFromStapel(p, card);
				cardGame.addCardToHand(p, card, false);
				cardGame.playSE(2); 
			}
		}
		cardGame.resolve();
    }
    
}
