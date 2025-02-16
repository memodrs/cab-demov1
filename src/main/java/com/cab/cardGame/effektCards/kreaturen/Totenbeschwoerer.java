package com.cab.cardGame.effektCards.kreaturen;

import java.util.List;
import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofAufBoard;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.PunkteArt;



public class Totenbeschwoerer extends CardStateEffekt {

	public Totenbeschwoerer(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomFriedhofAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), -2, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace() &&
			   cardGame.getOwnerOfCard(this).fluchCounter > 1 &&
			   cardGame.getOwnerOfCard(this).graveCards.stream().anyMatch(card -> Art.Nachtgestalt.equals(card.art));	
	}


	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).graveCards.stream().filter(card -> card.art == Art.Nachtgestalt).collect(Collectors.toList());
	}
}
