package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Klabautermann extends CardStateEffekt {
	public Klabautermann(Card card) {
		super(card, State.boardState, Trigger.triggerWurdeDurchAngriffZerstoert, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.specificKreaturAusStapelOderHandAufrufen(cardGame.getOwnerOfCard(this), Ids.PIRAT);
	}

	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace() && (cardGame.getOwnerOfCard(this).hasSpecificCardInHand(Ids.PIRAT) || cardGame.getOwnerOfCard(this).hasSpecificCardInStapel(Ids.PIRAT));
	};
}
