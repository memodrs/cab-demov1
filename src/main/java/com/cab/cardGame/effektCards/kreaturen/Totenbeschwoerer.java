package com.cab.cardGame.effektCards.kreaturen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Totenbeschwoerer extends CardStateEffekt {

	public Totenbeschwoerer(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVomFriedhofAufBoard(cardGame.player, id, true);
		cardGame.spielerPunkteAendern(cardGame.player, -2, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasBoardPlace() &&
			   p.fluchCounter > 1 &&
			   p.graveCards.stream().anyMatch(card -> Art.Nachtgestalt.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.graveCards.stream()
				.filter(card -> card.art == Art.Nachtgestalt)
				.collect(Collectors.toList())
		);
	}
}
