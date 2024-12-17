package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Kirin extends EffektCardState {

	public Kirin(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {		
		cardGame.setKarteStatus(id, true, Status.Blitz, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivateInTurn && cardGame.getOpOfP(p).boardCards.stream().anyMatch(card -> !card.isHide && !card.statusSet.contains(Status.Blitz));
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return !card.statusSet.contains(Status.Blitz) && !card.isHide;
	}
}
