package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Himmliche extends EffektCardState {

	public Himmliche(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {		
        int fluchPunkte = cardGame.player.fluchCounter;
		cardGame.spielerPunkteAendern(cardGame.player, -fluchPunkte, PunkteArt.Fluch, true);
		cardGame.spielerPunkteAendern(cardGame.player, fluchPunkte, PunkteArt.Segen, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.fluchCounter > 0;
	}
}	