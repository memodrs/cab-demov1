package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;



public class Prinzessin extends EffektCardState {
	public Prinzessin(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Integer id) {
		cardGame.karteVonHandAufFriedhof(cardGame.player, this.id, true);
		cardGame.karteVonStapelAufHand(cardGame.player, id, true);
		cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p) {
		return cardGame.containsSpecificCardId(p.stapel, Ids.KOENIG) || cardGame.containsSpecificCardId(p.stapel, Ids.HERRSCHERIN);
	}


	@Override
	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		Player p = cardGame.player;
		if (cardGame.containsSpecificCardId(p.stapel, Ids.KOENIG)) {
			cardGame.optionsCardsToSelect.add(cardGame.getCardOfSpecificId(Ids.KOENIG));
		}

		if (cardGame.containsSpecificCardId(p.stapel, Ids.HERRSCHERIN)) {
			cardGame.optionsCardsToSelect.add(cardGame.getCardOfSpecificId(Ids.HERRSCHERIN));
		}
    }
}
