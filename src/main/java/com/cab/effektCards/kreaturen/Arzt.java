package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Arzt extends EffektCardState {

	public Arzt(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.karteHeilen(cardGame.player, id, 2, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivateInTurn && p.boardCards.size() > 0;
	}

	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}
}
