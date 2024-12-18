package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Hypnose extends CardStateSpell {	
	public Hypnose(Card card) {
		super(card, State.boardState, State.effektSelectOponentBoardState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteBoardKontrolleUebernehmen(cardGame.player, id, true);	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasBoardPlace() && op.hasOpenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}
}