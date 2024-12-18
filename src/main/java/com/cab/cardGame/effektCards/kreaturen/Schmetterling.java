package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Schmetterling extends CardStateEffekt {

	public Schmetterling(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.kartenZiehen(cardGame.player, 1, true);

		if (cardGame.player.handCards.get(cardGame.player.handCards.size() - 1).art == Art.Fabelwesen) {
			cardGame.spielerPunkteAendern(cardGame.player, 1, PunkteArt.Segen, true);
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
