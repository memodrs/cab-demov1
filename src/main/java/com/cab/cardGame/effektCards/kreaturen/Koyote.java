package com.cab.cardGame.effektCards.kreaturen;

import java.util.List;
import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofInHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;




public class Koyote extends CardStateEffekt {

	public Koyote(Card card) {
		super(card, State.boardState, Trigger.triggerhatDurchAngriffZerstoert, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomFriedhofInHand().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).graveCards.stream()
		.anyMatch(card -> Art.Tier.equals(card.art));	
	}


	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).graveCards.stream().filter(card -> card.art == Art.Tier).collect(Collectors.toList());
	}
}
