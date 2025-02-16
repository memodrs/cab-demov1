package com.cab.cardGame.effektCards.kreaturen;

import java.util.List;
import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonStapelAufHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;




public class Schmied extends CardStateEffekt {

	public Schmied(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonStapelAufHand().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
		cardGame.kartenMischen(cardGame.getOwnerOfCard(this), cardGame.getOwnerOfCard(this).stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).stapel.stream()
		.anyMatch(card -> Art.Segen.equals(card.art));	
	}


	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).stapel.stream().filter(card -> card.art == Art.Segen).collect(Collectors.toList());
    }
}
