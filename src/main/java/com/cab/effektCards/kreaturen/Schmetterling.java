package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;



public class Schmetterling extends EffektCardState {

	public Schmetterling(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.kartenZiehen(cardGame.player, 1, true);

		if (cardGame.player.handCards.get(cardGame.player.handCards.size() - 1).art == Art.Fabelwesen) {
			cardGame.spielerPunkteAendern(cardGame.player, 1, PunkteArt.Segen, true);
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
