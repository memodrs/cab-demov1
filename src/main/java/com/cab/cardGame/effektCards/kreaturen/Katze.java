package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Katze extends CardStateEffekt {

	public Katze(Card card) {
		super(card, State.graveState, Trigger.triggerOnHandBeforeDamageDirekterAngriff, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVonHandAufFriedhof(cardGame.player, this.id, true);
		cardGame.spielerPunkteAendern(cardGame.player, 9, PunkteArt.Leben, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
