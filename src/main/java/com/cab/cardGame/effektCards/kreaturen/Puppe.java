package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;



public class Puppe extends CardStateEffekt {

	public Puppe(Card card) {
		super(card, State.graveState, Trigger.triggerOnHandDamageDirekterAngriff, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVonHandAufFriedhof(cardGame.player, this.id, true);
		cardGame.karteVomBoardInFriedhof(cardGame.oponent, id, true, false);
		cardGame.spielerPunkteAendern(cardGame.player, -1, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.fluchCounter > 0 && p.handCards.contains(this);
	}
}
