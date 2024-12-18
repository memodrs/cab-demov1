package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Gefallener extends CardStateEffekt {

	public Gefallener(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {		
        int segenPunkte = cardGame.player.segenCounter;
		cardGame.spielerPunkteAendern(cardGame.player, -segenPunkte, PunkteArt.Segen, true);
		cardGame.spielerPunkteAendern(cardGame.player, segenPunkte, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.segenCounter > 0;
	}
	
}