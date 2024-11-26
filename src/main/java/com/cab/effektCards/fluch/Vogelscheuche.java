package com.cab.effektCards.fluch;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Vogelscheuche extends EffektCardState {

	public Vogelscheuche(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide && card.art == Art.Tier) {
				cardGame.kreaturVomBoardInDieHandGeben(cardGame.oponent, card.id, true);
			}
		}	


	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasArtOnBoard(Art.Tier);
	}
}
