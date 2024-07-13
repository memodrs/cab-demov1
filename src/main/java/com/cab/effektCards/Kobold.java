package com.cab.effektCards;

import java.util.Random;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Kobold extends EffektCardState implements EffektCard {

	public Kobold(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		Random r = new Random();
		int opIdx = r.nextInt(cardGame.getOponentForPlayer(p).handCards.size());
		cardGame.kartenTauschenHand(p, this.id, cardGame.getOponentForPlayer(p).handCards.get(opIdx).id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivate && cardGame.getOponentForPlayer(p).handCards.size() > 0;
	}
}