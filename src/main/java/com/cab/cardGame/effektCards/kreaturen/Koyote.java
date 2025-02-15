package com.cab.cardGame.effektCards.kreaturen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofInHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Koyote extends CardStateEffekt {

	public Koyote(Card card) {
		super(card, State.boardState, Trigger.triggerhatDurchAngriffZerstoert, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomFriedhofInHand().execute(cardGame, cardGame.player, id, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.graveCards.stream()
		.anyMatch(card -> Art.Tier.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.graveCards.stream()
				.filter(card -> card.art == Art.Tier)
				.collect(Collectors.toList())
		);
	}
}
