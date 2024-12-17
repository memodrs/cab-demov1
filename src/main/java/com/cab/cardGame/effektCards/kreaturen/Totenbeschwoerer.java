package com.cab.cardGame.effektCards.kreaturen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;



public class Totenbeschwoerer extends EffektCardState {

	public Totenbeschwoerer(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Integer id) {
		cardGame.karteVomFriedhofAufBoard(cardGame.player, id, true);
		cardGame.spielerPunkteAendern(cardGame.player, -2, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() &&
			   p.fluchCounter > 1 &&
			   p.graveCards.stream().anyMatch(card -> Art.Nachtgestalt.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect() {
		super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.graveCards.stream()
				.filter(card -> card.art == Art.Nachtgestalt)
				.collect(Collectors.toList())
		);
	}
}