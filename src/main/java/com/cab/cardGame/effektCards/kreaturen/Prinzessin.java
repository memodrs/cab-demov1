package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Prinzessin extends CardStateEffekt {
	public Prinzessin(Card card) {
		super(card, State.handCardState, Trigger.triggerManualFromHand, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVonHandAufFriedhof(cardGame.player, this.id, true);
		cardGame.karteVonStapelAufHand(cardGame.player, id, true);
		cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasSpecificCardInStapel(Ids.KOENIG) || p.hasSpecificCardInStapel(Ids.HERRSCHERIN);
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		Player p = cardGame.player;
		if (p.hasSpecificCardInStapel(Ids.KOENIG)) {
			cardGame.optionsCardsToSelect.add(cardGame.getCardOfSpecificId(Ids.KOENIG));
		}

		if (p.hasSpecificCardInStapel(Ids.HERRSCHERIN)) {
			cardGame.optionsCardsToSelect.add(cardGame.getCardOfSpecificId(Ids.HERRSCHERIN));
		}
    }
}
