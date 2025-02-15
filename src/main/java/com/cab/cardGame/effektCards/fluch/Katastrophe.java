package com.cab.cardGame.effektCards.fluch;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Katastrophe extends CardStateSpell {	
	public Katastrophe(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		List<Integer> idsToDestroy;

		idsToDestroy = new ArrayList<>();
		for (CardState card : cardGame.getOpOfCard(this).boardCards) {
			if (card.art == Art.Mensch) {
				idsToDestroy.add(card.id);
			}
		}
		for (Integer idToDestroy : idsToDestroy) {
			new KarteVomBoardInFriedhof().execute(cardGame, cardGame.getOpOfCard(this), idToDestroy, true, false);
		}
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasArtOnBoard(Art.Fabelwesen)|| cardGame.getOpOfCard(this).hasArtOnBoard(Art.Mensch);
	}
}