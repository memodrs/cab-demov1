package com.cab.cardGame.effektCards.kreaturen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;



public class Spirituelle extends CardStateEffekt {

	public Spirituelle(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonHandAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, false, true, true);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), -2, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace() &&
			   cardGame.getOwnerOfCard(this).segenCounter > 1 &&
			   cardGame.getOwnerOfCard(this).handCards.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.getOwnerOfCard(this).handCards.stream()
				.filter(card -> card.art == Art.Fabelwesen)
				.collect(Collectors.toList())
		);
	}
}
