package com.cab.cardGame.effektCards.fluch;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Vogelscheuche extends CardStateSpell {

	public Vogelscheuche(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		List<Integer> idsToRemoveFromBord = new ArrayList<>();

		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide && card.art == Art.Tier) {
				idsToRemoveFromBord.add(card.id);
			}
		}	

		for (Integer cardId : idsToRemoveFromBord) {
			cardGame.karteVonBoardInHand(cardGame.oponent, cardId, true);
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.hasArtOnBoard(Art.Tier);
	}
}
