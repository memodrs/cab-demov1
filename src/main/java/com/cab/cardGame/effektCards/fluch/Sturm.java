package com.cab.cardGame.effektCards.fluch;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Sturm extends CardStateSpell {	
	public Sturm(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		List<Integer> idsToDestroy;

		idsToDestroy = new ArrayList<>();
		for (CardState card : cardGame.player.boardCards) {
			idsToDestroy.add(card.id);
		}
		for (Integer idToDestroy : idsToDestroy) {
			new KarteVomBoardInFriedhof().execute(cardGame, cardGame.player, idToDestroy, true, false);
		}

		idsToDestroy = new ArrayList<>();
		for (CardState card : cardGame.oponent.boardCards) {
			idsToDestroy.add(card.id);
		}
		for (Integer idToDestroy : idsToDestroy) {
			new KarteVomBoardInFriedhof().execute(cardGame, cardGame.oponent, idToDestroy, true, false);
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return !p.isBoardEmpty() || !op.isBoardEmpty();
	}
}