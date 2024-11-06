package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Taube extends EffektCardState implements EffektCard {

	public Taube(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
        cardGame.spielerPunkteAendern(p, 1, PunkteArt.Segen, true);
        cardGame.forceOponentToEndTurn();
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
