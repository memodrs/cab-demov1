package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;



public class Verfluchter extends EffektCardState {

	public Verfluchter(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		Player op = cardGame.oponent;
		if (op.segenCounter > 0) {
			cardGame.spielerPunkteAendern(op, -1, PunkteArt.Segen, true);
		}
		cardGame.spielerPunkteAendern(cardGame.player, 1, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
