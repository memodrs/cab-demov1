package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Gefallener extends CardStateEffekt {

	public Gefallener(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {		
        int segenPunkte = cardGame.player.segenCounter;
		cardGame.spielerPunkteAendern(cardGame.player, -segenPunkte, PunkteArt.Segen, true);
		cardGame.spielerPunkteAendern(cardGame.player, segenPunkte, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.segenCounter > 0;
	}
	
}