package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Gespenst extends EffektCardState {

	public Gespenst(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		cardGame.spielerPunkteAendern(cardGame.player, -1, PunkteArt.Fluch, true);	
		cardGame.karteHeilen(cardGame.player, this.id, cardGame.getCardOfId(id).atk, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.fluchCounter > 0;
	}
}