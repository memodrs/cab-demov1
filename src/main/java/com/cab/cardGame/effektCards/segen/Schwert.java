package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Schwert extends CardStateSpell {

	public Schwert(Card card) {
		super(card, State.boardState, State.effektSelectOwnBoardState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteAngriffErhoehen(id, 4, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasOpenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
        return !card.isHide;
    }
}