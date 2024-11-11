package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;



public class Verfluchter extends EffektCardState {

	public Verfluchter(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		Player op = cardGame.getOpOfP(p);
		if (op.segenCounter > 0) {
			cardGame.spielerPunkteAendern(op, -1, PunkteArt.Segen, true);
		}
		cardGame.spielerPunkteAendern(p, 1, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
