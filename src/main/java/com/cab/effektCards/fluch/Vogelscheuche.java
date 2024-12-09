package com.cab.effektCards.fluch;

import java.util.ArrayList;
import java.util.List;

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
		List<Integer> idsToRemoveFromBord = new ArrayList<>();

		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide && card.art == Art.Tier) {
				idsToRemoveFromBord.add(card.id);
			}
		}	

		for (Integer cardId : idsToRemoveFromBord) {
			cardGame.kreaturVomBoardInDieHandGeben(cardGame.oponent, cardId, true);
		}


	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasArtOnBoard(Art.Tier);
	}
}
