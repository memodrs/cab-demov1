package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;



public class Puppe extends EffektCardState {

	public Puppe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.karteVonHandAufFriedhof(cardGame.player, this.id, true);
		cardGame.karteVomBoardInFriedhof(cardGame.oponent, id, true, false);
		cardGame.spielerPunkteAendern(cardGame.player, -1, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.fluchCounter > 0;
	}
}
