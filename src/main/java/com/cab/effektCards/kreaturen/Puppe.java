package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Puppe extends EffektCardState {

	public Puppe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		System.out.println(cardGame.getCardOfId(id).defaultCard.getName());
		cardGame.karteVonHandZerstoeren(cardGame.player, this.id, true);
		cardGame.kreaturVomBoardZerstoeren(cardGame.oponent, id, true, false);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
