package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;



public class Katze extends EffektCardState {

	public Katze(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		cardGame.karteVonHandZerstoeren(p, this.id, true);
		cardGame.spielerPunkteAendern(p, 9, PunkteArt.Leben, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
