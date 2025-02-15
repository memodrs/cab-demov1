package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;



public class HoellenReiter extends CardStateEffekt {

	public HoellenReiter(Card card) {
			super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
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
		new SpielerPunkteAendern().execute(cardGame, cardGame.player, counterFluchpunkte, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
