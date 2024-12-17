package com.cab.cardGame.effektCards.kreaturen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;



public class Spirituelle extends EffektCardState {

	public Spirituelle(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Integer id) {
		cardGame.karteVonHandAufBoard(cardGame.player, id, false, true, true);
		cardGame.spielerPunkteAendern(cardGame.player, -2, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() &&
			   p.segenCounter > 1 &&
			   p.handCards.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect() {
		super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.handCards.stream()
				.filter(card -> card.art == Art.Fabelwesen)
				.collect(Collectors.toList())
		);
	}
}
