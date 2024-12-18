package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Scharmanin extends CardStateEffekt {

	public Scharmanin(Card card) {
		super(card, State.boardState, Trigger.triggerKarteWurdeDurchKampfZerstoert, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVomFriedhofAufBoard(cardGame.player, id, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.graveCards.stream()
		.anyMatch(card -> Art.Tier.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		for (CardState card : cardGame.player.graveCards) {
			if (card.art == Art.Tier) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}
