package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KartenZiehen {
    public void execute(CardGame cardGame, Player player, int numberOfCards, boolean send) {
		cardGame.send(send, player.isPlayer, numberOfCards, null, null, null, null, null, null, Messages.KARTEN_ZIEHEN);

        for (int i = 0; i < numberOfCards; i++) {
			if (player.stapel.isEmpty() || player.handCards.size() >= cardGame.limitCardsInHand) {
				break;
			}
	
			int index = player.stapel.size() - 1;
			CardState card = player.stapel.get(index);

			if (cardGame.isCardInStapel(card)) {
				cardGame.removeCardFromStapel(player, card);
				cardGame.addCardToHand(player, card, false);
				cardGame.playSE(2); 
			}
		}
		cardGame.resolve();
    }
    
}
