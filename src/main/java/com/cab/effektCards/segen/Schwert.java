package com.cab.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Schwert extends EffektCardState {

	public Schwert(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
		cardGame.karteAngriffErhoehen(p, idx, 4, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.hasPlayerOpenCardsOnBoard(p);
	}

	public boolean isCardValidForSelection(CardState card) {
        return !card.isHide;
    }
}