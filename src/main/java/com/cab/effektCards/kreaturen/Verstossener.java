package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;



public class Verstossener extends EffektCardState {

	public Verstossener(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.karteVonHandZerstoeren(cardGame.player, this.id, true);
		cardGame.spielerPunkteAendern(cardGame.player, 2, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
