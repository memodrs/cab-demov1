package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Kirin extends CardStateEffekt {

	public Kirin(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.effektSelectOponentBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		cardGame.setKarteStatus(id, true, Status.Blitz, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.boardCards.stream().anyMatch(card -> !card.isHide && !card.statusSet.contains(Status.Blitz));
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return !card.statusSet.contains(Status.Blitz) && !card.isHide;
	}
}
