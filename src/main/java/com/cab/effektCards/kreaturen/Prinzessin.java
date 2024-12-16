package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Prinzessin extends EffektCardState {
	final int KOENIG_ID = 100;
	final int HERRSCHERIN_ID = 107;

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
		return cardGame.containsSpecificCardId(p.stapel, KOENIG_ID) || cardGame.containsSpecificCardId(p.stapel, HERRSCHERIN_ID);
	}


	@Override
	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		Player p = cardGame.player;
		if (cardGame.containsSpecificCardId(p.stapel, KOENIG_ID)) {
			cardGame.optionsCardsToSelect.add(cardGame.getCardOfSpecificId(KOENIG_ID));
		}

		if (cardGame.containsSpecificCardId(p.stapel, HERRSCHERIN_ID)) {
			cardGame.optionsCardsToSelect.add(cardGame.getCardOfSpecificId(HERRSCHERIN_ID));
		}
    }
}
