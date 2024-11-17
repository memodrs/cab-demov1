package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Gefallener extends EffektCardState {

	public Gefallener(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {		
        int segenPunkte = p.segenCounter;
		cardGame.spielerPunkteAendern(p, -segenPunkte, PunkteArt.Segen, true);
		cardGame.spielerPunkteAendern(p, segenPunkte, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.segenCounter > 0;
	}
	
}