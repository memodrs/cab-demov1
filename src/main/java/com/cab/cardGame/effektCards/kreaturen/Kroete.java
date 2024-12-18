package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Kroete extends CardStateEffekt {

	public Kroete(Card card) {
		super(card, State.graveOponentState, Trigger.triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		cardGame.karteVomBoardInFriedhof(cardGame.oponent, id, true, false);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}