package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Pirat extends EffektCardState implements EffektCard {

	public Pirat(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
		cardGame.spielerPunkteAendern(cardGame.getOponentForPlayer(p), -2, PunkteArt.Segen, true);
		cardGame.spielerPunkteAendern(p, 2, PunkteArt.Segen, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOponentForPlayer(p).segenCounter > 1;
	}
}
