package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Irrlicht extends CardStateEffekt {

	public Irrlicht(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
        cardGame.karteVomBoardInFriedhof(cardGame.oponent, id, true, false);
    }
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).boardCards.size() > 0;
	}
}
