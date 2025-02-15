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
import com.cab.cardGame.model.Player;

public class Ueberstrahlung extends CardStateSpell {	
	public Ueberstrahlung(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		List<Integer> idsToDestroy;

		idsToDestroy = new ArrayList<>();
		for (CardState card : cardGame.player.boardCards) {
			if (card.art == Art.Nachtgestalt) {
				idsToDestroy.add(card.id);
			}
		}
		for (Integer idToDestroy : idsToDestroy) {
			new KarteVomBoardInFriedhof().execute(cardGame, cardGame.player, idToDestroy, true, false);
		}

		idsToDestroy = new ArrayList<>();
		for (CardState card : cardGame.oponent.boardCards) {
			if (card.art == Art.Nachtgestalt) {
				idsToDestroy.add(card.id);
			}
		}
		for (Integer idToDestroy : idsToDestroy) {
			new KarteVomBoardInFriedhof().execute(cardGame, cardGame.oponent, idToDestroy, true, false);
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasArtOnBoard(Art.Nachtgestalt) || op.hasArtOnBoard(Art.Nachtgestalt);
	}
}