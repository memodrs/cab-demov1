package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Magier extends EffektCardState {

	public Magier(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {		
		cardGame.spielerPunkteAendern(cardGame.player, -2, PunkteArt.Fluch, true);
		cardGame.karteVonBoardInHand(cardGame.oponent, id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.fluchCounter > 0 && cardGame.getOpOfP(p).hasOpenCardsOnBoard();
	}
}