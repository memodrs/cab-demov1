package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Himmliche extends CardStateEffekt {

	public Himmliche(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {		
        int fluchPunkte = cardGame.player.fluchCounter;
		cardGame.spielerPunkteAendern(cardGame.player, -fluchPunkte, PunkteArt.Fluch, true);
		cardGame.spielerPunkteAendern(cardGame.player, fluchPunkte, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.fluchCounter > 0;
	}
}	