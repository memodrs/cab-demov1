package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Kirin extends EffektCardState {

	public Kirin(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {		
		cardGame.setKarteStatus(cardGame.getOpOfP(p), id, true, Status.Blitz, true);
	}
	
	public boolean isEffektPossible(Player p) {
		boolean res = false;
		for (int i = 0; i < cardGame.getOpOfP(p).boardCards.size(); i++) {
			if (!cardGame.getOpOfP(p).boardCards.get(i).isHide && !cardGame.getOpOfP(p).boardCards.get(i).statusSet.contains(Status.Blitz)) {
				res = true;
				break;
			}
		}
		return !isEffectActivateInTurn && res;
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return !card.statusSet.contains(Status.Blitz) && !card.isHide;
	}
}
