package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KartenZiehen;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;



public class Schmetterling extends CardStateEffekt {

	public Schmetterling(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KartenZiehen().execute(cardGame, cardGame.player, 1, true);
		
		if (cardGame.player.handCards.get(cardGame.player.handCards.size() - 1).art == Art.Fabelwesen) {
			new SpielerPunkteAendern().execute(cardGame, cardGame.player, 1, PunkteArt.Segen, true);
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
