package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Drache extends EffektCardState implements EffektCard {

	public Drache(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {		
		cardGame.setKarteStatus(cardGame.getOponentForPlayer(p), id, true, Status.Feuer, true);
	}
	
	public boolean isEffektPossible(Player p) {
		boolean res = false;
		for (int i = 0; i < cardGame.getOponentForPlayer(p).boardCards.size(); i++) {
			if (!cardGame.getOponentForPlayer(p).boardCards.get(i).isHide && cardGame.getOponentForPlayer(p).boardCards.get(i).status != Status.Feuer) {
				res = true;
				break;
			}
		}
		return !isEffectActivateInTurn && res;
	}
	
	public boolean isCardValidForSelection(CardState card) {
		boolean res = (card.status != Status.Feuer && !card.isHide);
		if (!res) {
			cardGame.cd.showMsg("Bitte eine offene Karte wählen, die noch nicht brennt");
		}
		return res;
	}
}
