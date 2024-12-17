package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Taube extends EffektCardState {

	public Taube(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
        cardGame.spielerPunkteAendern(cardGame.player, 1, PunkteArt.Segen, true);
        cardGame.forceOponentToEndTurn();
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}