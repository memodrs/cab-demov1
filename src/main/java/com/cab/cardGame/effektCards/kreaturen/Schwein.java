package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;


public class Schwein extends CardStateEffekt {

	public Schwein(Card card) {
		super(card, State.handCardState, Trigger.triggerManualFromHand, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		cardGame.karteVonHandAufFriedhof(cardGame.player, this.id, true);
		for (CardState card : cardGame.player.boardCards) {
			if (card.art == Art.Mensch && !card.isHide) {
				cardGame.karteAngriffErhoehen(card.id, 2, true);
				cardGame.karteHeilen(card.id, 2, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasArtOnBoard(Art.Mensch);
	}
}