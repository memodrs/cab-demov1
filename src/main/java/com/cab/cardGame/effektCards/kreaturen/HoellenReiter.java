package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;



public class HoellenReiter extends EffektCardState {

	public HoellenReiter(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		int counterFluchpunkte = 0;

		for (CardState card : cardGame.player.boardCards) {
			if (!card.isHide && card.art == Art.Nachtgestalt) {
				counterFluchpunkte++;
			}
		}

		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide && card.art == Art.Nachtgestalt) {
				counterFluchpunkte++;
			}
		}
		cardGame.spielerPunkteAendern(cardGame.player, counterFluchpunkte, PunkteArt.Fluch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}