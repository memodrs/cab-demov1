package com.cab.cardGame.effektCards.kreaturen;

import java.util.Random;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Kobold extends EffektCardState {

	public Kobold(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		Random r = new Random();
		int opIdx = r.nextInt(cardGame.oponent.handCards.size());
		cardGame.kartenTauschenHand(cardGame.player, this.id, cardGame.oponent.handCards.get(opIdx).id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivate && cardGame.getOpOfP(p).handCards.size() > 0;
	}
}