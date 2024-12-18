package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Brand extends CardStateSpell {
	public Brand(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		for (CardState card : cardGame.player.boardCards) {
			if (!card.isHide) {
				cardGame.setKarteStatus(card.id, true, Status.Feuer, true);
			}
		}	

		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide) {
				cardGame.setKarteStatus(card.id, true, Status.Feuer, true);
			}
		}	
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasOpenCardsOnBoard() || op.hasOpenCardsOnBoard();
	}
}
