package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Quelle extends CardStateSpell {

	public Quelle(Card card) {
		super(card, State.boardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		for (CardState card : cardGame.player.boardCards) {
			if (!card.isHide) {
				cardGame.karteHeilen(card.id, 2, true);
				cardGame.setKarteStatus(card.id, false, Status.Feuer, true);
				cardGame.setKarteStatus(card.id, false, Status.Blitz, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasOpenCardsOnBoard();
	}
}