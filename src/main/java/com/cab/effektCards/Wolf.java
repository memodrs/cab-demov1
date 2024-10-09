package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Wolf extends EffektCardState implements EffektCard {


	
	public Wolf(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		for (int i = 0; i < p.handCards.size(); i++) {
			if (p.stapel.get(i).defaultCard.id == 2) {
				p.handCards.get(i).isEffectActivateInTurn = true;
				cardGame.kreaturAufrufen(p, i, false, true, true);
			}
		}
	}

	public boolean isEffektPossible(Player p) {
		boolean isWolfInHand = false;

		for (CardState card : p.handCards) {
			if (card.defaultCard.id == 2) {
				isWolfInHand = true;
				break;
			}
		}	
		
		return isWolfInHand && p.boardCards.size() < 4 && !isEffectActivateInTurn;
	};
}
