package com.cab.effektCards.kreaturen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Koyote extends EffektCardState {

	public Koyote(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Integer id) {
		cardGame.kreaturVomFriedhofInDieHandNehmen(cardGame.player, id, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p) {
		return p.graveCards.stream()
		.anyMatch(card -> Art.Tier.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect() {
		super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.graveCards.stream()
				.filter(card -> card.art == Art.Tier)
				.collect(Collectors.toList())
		);
	}
}
