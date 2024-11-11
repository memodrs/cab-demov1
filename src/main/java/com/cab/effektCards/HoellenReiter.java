package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;



public class HoellenReiter extends EffektCardState {

	public HoellenReiter(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		int counterFluchpunkte = 0;

		for (CardState card : p.boardCards) {
			if (this.id != card.id && card.art == Art.Nachtgestalt) {
				counterFluchpunkte++;
			}
		}

		for (CardState card : cardGame.getOpOfP(p).boardCards) {
			if (this.id != card.id && card.art == Art.Nachtgestalt) {
				counterFluchpunkte++;
			}
		}
		cardGame.spielerPunkteAendern(p, counterFluchpunkte, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
