package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Kessel extends CardStateSpell {

	public Kessel(Card card, CardGame cardGame, int nextStateForPlayer, int selectState) {
		super(card, cardGame, nextStateForPlayer, selectState);
	}


	public void effekt(Integer id) {
        int size = cardGame.player.handCards.size();

        for (int i = 0; i < size; i++) {
			cardGame.karteVonHandAufStapel(cardGame.player, 0, true);
        }
        cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
        cardGame.kartenZiehen(cardGame.player, size, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.handCards.size() > 1;
	}
}