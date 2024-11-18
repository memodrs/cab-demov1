package com.cab.effektCards.kreaturen;

import java.util.Random;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Kraken extends EffektCardState {

	public Kraken(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		Random r = new Random();
		Player op = cardGame.getOpOfP(p);
		int idx = r.nextInt(op.handCards.size());
		cardGame.karteVonHandZerstoeren(op, op.handCards.get(idx).id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).handCards.size() > 0;
	}
}
