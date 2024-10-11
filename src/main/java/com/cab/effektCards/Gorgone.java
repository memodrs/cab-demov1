package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Gorgone extends EffektCardState implements EffektCard {

	public Gorgone(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
        for (int i = 0; i < cardGame.getOponentForPlayer(p).boardCards.size(); i++) {
            //TODO BUG das funktioniert so nicht, da dass online nicht uebertragen wird!
            cardGame.getOponentForPlayer(p).boardCards.get(i).hasAttackOnTurn = true;
        }
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}