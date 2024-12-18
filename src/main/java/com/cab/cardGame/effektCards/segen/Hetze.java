package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Hetze extends CardStateSpell {

	public Hetze(Card card) {
		super(card, State.boardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        for (CardState card : cardGame.player.boardCards) {
            card.hasAttackOnTurn = false;
        }
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return p.boardCards.stream().anyMatch(card -> card.hasAttackOnTurn);	
    }
}