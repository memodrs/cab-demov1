package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;



public class Gefangener extends EffektCardState {

	public Gefangener(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		if (this.wasPlayedInTurn && cardGame.player.boardCards.contains(this)) {
			cardGame.kreaturVomBoardZerstoeren(cardGame.player, this.id, true, false);
		}
		cardGame.spielerPunkteAendern(cardGame.player, 2, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
