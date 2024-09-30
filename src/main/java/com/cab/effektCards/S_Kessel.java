package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class S_Kessel extends EffektCardState implements EffektCard {

	public S_Kessel(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
        int size = p.handCards.size();

        for (int i = 0; i < size; i++) {
			cardGame.karteVonHandAufDenStapel(p, 0, true);
        }
        cardGame.kartenMischen(p, p.stapel, true);
        cardGame.kartenZiehen(p, size, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.handCards.size() > 1;
	}
}