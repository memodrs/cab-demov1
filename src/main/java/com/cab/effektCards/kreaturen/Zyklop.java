package com.cab.effektCards.kreaturen;

import java.util.Random;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Zyklop extends EffektCardState {


	
	public Zyklop(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
		Random r = new Random();
		int randomIndex = r.nextInt(cardGame.getOpOfP(p).boardCards.size());
		cardGame.changeSavedIdOponentAttack(cardGame.getOpOfP(p).boardCards.get(randomIndex).id, true);
	}

	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).boardCards.size() > 0;
	};
}