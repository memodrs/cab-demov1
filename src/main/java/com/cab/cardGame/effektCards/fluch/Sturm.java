package com.cab.cardGame.effektCards.fluch;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Sturm extends EffektCardState {	
	public Sturm(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}
	
	public void effekt(Integer id) {
		List<Integer> idsToDestroy;

		idsToDestroy = new ArrayList<>();
		for (CardState card : cardGame.player.boardCards) {
			idsToDestroy.add(card.id);
		}
		for (Integer idToDestroy : idsToDestroy) {
			cardGame.karteVomBoardInFriedhof(cardGame.player, idToDestroy, true, false);
		}

		idsToDestroy = new ArrayList<>();
		for (CardState card : cardGame.oponent.boardCards) {
			idsToDestroy.add(card.id);
		}
		for (Integer idToDestroy : idsToDestroy) {
			cardGame.karteVomBoardInFriedhof(cardGame.oponent, idToDestroy, true, false);
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return !p.isBoardEmpty() || !cardGame.getOpOfP(p).isBoardEmpty();
	}
}