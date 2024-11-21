package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Minotaurus extends EffektCardState {

	public Minotaurus(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		cardGame.spielerPunkteAendern(cardGame.oponent, -2, PunkteArt.Leben, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}