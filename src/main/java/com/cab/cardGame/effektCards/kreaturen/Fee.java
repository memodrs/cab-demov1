package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Fee extends CardStateEffekt {

	public Fee(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {		
		cardGame.spielerPunkteAendern(cardGame.player, cardGame.player.boardCards.size(), PunkteArt.Leben, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivateInTurn;
	}
}	