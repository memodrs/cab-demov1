package com.cab.cardGame.effektCards.fluch;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonBoardInHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Vogelscheuche extends CardStateSpell {

	public Vogelscheuche(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		List<Integer> idsToRemoveFromBord = new ArrayList<>();

		for (CardState card : cardGame.getOpOfCard(this).boardCards) {
			if (!card.isHide && card.art == Art.Tier) {
				idsToRemoveFromBord.add(card.id);
			}
		}	

		for (Integer cardId : idsToRemoveFromBord) {
			new KarteVonBoardInHand().execute(cardGame, cardGame.getOpOfCard(this), cardId, true);
		}
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOpOfCard(this).hasArtOnBoard(Art.Tier);
	}
}
