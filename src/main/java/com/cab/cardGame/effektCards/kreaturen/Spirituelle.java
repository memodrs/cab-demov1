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



public class Spirituelle extends CardStateEffekt {

	public Spirituelle(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVonHandAufBoard(cardGame.player, id, false, true, true);
		cardGame.spielerPunkteAendern(cardGame.player, -2, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasBoardPlace() &&
			   p.segenCounter > 1 &&
			   p.handCards.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.handCards.stream()
				.filter(card -> card.art == Art.Fabelwesen)
				.collect(Collectors.toList())
		);
	}
}
