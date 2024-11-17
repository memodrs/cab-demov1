package com.cab.effektCards.kreaturen;

import java.util.Random;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Kobold extends EffektCardState {

	public Kobold(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		Random r = new Random();
		int opIdx = r.nextInt(cardGame.getOpOfP(p).handCards.size());
		cardGame.kartenTauschenHand(p, this.id, cardGame.getOpOfP(p).handCards.get(opIdx).id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivate && cardGame.getOpOfP(p).handCards.size() > 0;
	}
}