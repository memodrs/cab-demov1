package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Sturmangriff extends CardStateSpell {

	public Sturmangriff(Card card) {
		super(card, State.boardState, State.effektSelectOwnBoardState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        cardGame.getCardOfId(id).hasAttackOnTurn = false;
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return p.boardCards.stream().anyMatch(card -> card.hasAttackOnTurn);	
    }

    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && card.hasAttackOnTurn;
    }
}