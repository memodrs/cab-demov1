package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Hexe extends EffektCardState implements EffektCard {

	public Hexe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {		
		cardGame.karteVomBoardInDieHandGeben(cardGame.getOponentForPlayer(p), id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.hasPlayerOpenCardsOnBoard(cardGame.getOponentForPlayer(p));
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}
}
