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
		int idx = -1;
		for (int i = 0; i < p.stapel.size(); i++) {
			if (p.stapel.get(i).defaultCard.id == 58) {
				idx = i;
			}
		}
		cardGame.kreaturAufrufenVomStapel(p, idx, true);
	}

	public boolean isEffektPossible(Player p) {
		boolean isBabayWoldImStapel = false;

		for (CardState card : p.stapel) {
			if (card.defaultCard.id == 58) {
				isBabayWoldImStapel = true;
			}
		}	
		
		return isBabayWoldImStapel && p.boardCards.size() < 4 && !isEffectActivateInTurn;
	};
}
