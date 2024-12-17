package com.cab.cardGame.effektCards.kreaturen;

import java.util.Random;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;



public class Oktopus extends EffektCardState {

	public Oktopus(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		Random r = new Random();
		Player op = cardGame.oponent;
		int idx = r.nextInt(op.handCards.size());
		cardGame.karteVonHandAufFriedhof(op, op.handCards.get(idx).id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).handCards.size() > 0;
	}
}