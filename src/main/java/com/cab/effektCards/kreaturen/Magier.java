package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Magier extends EffektCardState {

	public Magier(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {		
		cardGame.spielerPunkteAendern(p, -1, PunkteArt.Fluch, true);
		cardGame.kreaturVomFriedhofAufrufen(cardGame.getOpOfP(p), id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.fluchCounter > 0 && cardGame.getOpOfP(p).boardCards.size() > 0;
	}
}