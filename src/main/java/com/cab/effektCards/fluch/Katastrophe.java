package com.cab.effektCards.fluch;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Katastrophe extends EffektCardState {	
	public Katastrophe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}
	
	public void effekt(Integer id) {
		List<Integer> idsToDestroy;

		idsToDestroy = new ArrayList<>();
		for (CardState card : cardGame.oponent.boardCards) {
			if (card.art == Art.Mensch) {
				idsToDestroy.add(card.id);
			}
		}
		for (Integer idToDestroy : idsToDestroy) {
			cardGame.karteVomBoardInFriedhof(cardGame.oponent, idToDestroy, true, false);
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return p.hasArtOnBoard(Art.Fabelwesen)|| cardGame.getOpOfP(p).hasArtOnBoard(Art.Mensch);
	}
}