package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Hirsch extends EffektCardState {


	
	public Hirsch(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {
		cardGame.kreaturAufrufen(cardGame.player, this.id, false, true, true);
		cardGame.spielerPunkteAendern(cardGame.player, -1, PunkteArt.Segen, true);
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && p.segenCounter > 0;
	};
}
