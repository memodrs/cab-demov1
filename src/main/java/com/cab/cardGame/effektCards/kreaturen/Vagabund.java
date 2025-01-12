package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Vagabund extends CardStateEffekt {

	public Vagabund(Card card) {
		super(card, State.handCardState, Trigger.triggerManualFromHand, State.effektSelectOwnBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		cardGame.karteVonBoardInHand(cardGame.player, id, true);
		cardGame.karteVonHandAufBoard(cardGame.player, id, false, true, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return !p.isBoardEmpty();
	}
}
