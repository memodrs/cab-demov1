package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Magier extends EffektCardState implements EffektCard {

	public Magier(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {		
		cardGame.spielerPunkteAendern(p, -1, PunkteArt.Fluch, true);
		cardGame.karteVomBoardInDieHandGeben(cardGame.getOponentForPlayer(p), id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivateInTurn && p.fluchCounter > 0 && cardGame.getOponentForPlayer(p).boardCards.size() > 0;
	}
}