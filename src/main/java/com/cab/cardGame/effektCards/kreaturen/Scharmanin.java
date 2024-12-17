package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;



public class Scharmanin extends EffektCardState {

	public Scharmanin(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Integer id) {
		cardGame.karteVomFriedhofAufBoard(cardGame.player, id, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p) {
		return p.graveCards.stream()
		.anyMatch(card -> Art.Tier.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		for (CardState card : cardGame.player.graveCards) {
			if (card.art == Art.Tier) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}
