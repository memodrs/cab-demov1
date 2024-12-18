package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Drache extends CardStateEffekt {

	public Drache(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.effektSelectOponentBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		cardGame.setKarteStatus(id, true, Status.Feuer, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		boolean res = false;
		for (int i = 0; i < op.boardCards.size(); i++) {
			if (!op.boardCards.get(i).isHide && !op.boardCards.get(i).statusSet.contains(Status.Feuer)) {
				res = true;
				break;
			}
		}
		return !isEffectActivateInTurn && res;
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return !card.statusSet.contains(Status.Feuer) && !card.isHide;
	}
}
