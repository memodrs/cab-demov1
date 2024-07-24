package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;;

public class Haenker extends EffektCardState implements EffektCard {

	public Haenker(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		cardGame.karteVomBoardZerstoeren(cardGame.getOponentForPlayer(p), id, true, false);
	}
	
	public boolean isEffektPossible(Player p) {
		Player op = cardGame.getOponentForPlayer(p);
		return cardGame.isArtOnBoardOfPlayer(op, Art.Mensch);
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide && card.art == Art.Mensch;
	}
}