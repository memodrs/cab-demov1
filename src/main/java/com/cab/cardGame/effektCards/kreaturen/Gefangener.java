package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Gefangener extends CardStateEffekt {

	public Gefangener(Card card) {
		super(card, State.boardState, Trigger.triggerAfterDoAttack, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		if (this.wasPlayedInTurn) {
			cardGame.karteVomBoardInFriedhof(cardGame.player, this.id, true, false);
		}
		cardGame.spielerPunkteAendern(cardGame.player, 2, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
