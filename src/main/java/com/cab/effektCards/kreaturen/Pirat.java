package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Pirat extends EffektCardState {

	public Pirat(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {
		cardGame.spielerPunkteAendern(cardGame.oponent, -2, PunkteArt.Segen, true);
		cardGame.spielerPunkteAendern(cardGame.player, 2, PunkteArt.Segen, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).segenCounter > 1;
	}
}
