package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;




public class Scharmanin extends CardStateEffekt {

	public Scharmanin(Card card) {
		super(card, State.boardState, Trigger.triggerWurdeDurchAngriffZerstoert, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomFriedhofAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).graveCards.stream()
		.anyMatch(card -> Art.Tier.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		for (CardState card : cardGame.getOwnerOfCard(this).graveCards) {
			if (card.art == Art.Tier) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}
