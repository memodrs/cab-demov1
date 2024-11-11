package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Fee extends EffektCardState {

	public Fee(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {		
		cardGame.spielerPunkteAendern(p, p.boardCards.size(), PunkteArt.Leben, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivateInTurn;
	}
}	