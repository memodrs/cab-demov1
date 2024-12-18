package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Rabe extends CardStateEffekt {
	final int ID_HEXE = 1;
	
	public Rabe(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		CardState searchCard = cardGame.getCardOfSpecificId(ID_HEXE);
		cardGame.karteVonHandAufBoard(cardGame.player, searchCard.id, false, true, true);
	}

	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasBoardPlace() && p.hasSpecificCardInHand(Ids.HEXE);
	};
}
