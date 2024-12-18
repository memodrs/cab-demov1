package com.cab.cardGame.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class RufDerDunkelheit extends CardStateSpell {

	public RufDerDunkelheit(Card card, CardGame cardGame, int nextStateForPlayer, int selectState) {
		super(card, cardGame, nextStateForPlayer, selectState);
	}


	public void effekt(Integer id) {	
		cardGame.karteVomFriedhofAufBoard(cardGame.player, id, true);
	}
	
    public boolean isEffektPossible(Player p) {
		return  p.hasBoardPlace() && p.graveCards.stream().anyMatch(card -> Art.Nachtgestalt.equals(card.art));
    }

	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		cardGame.optionsCardsToSelect.addAll(
			cardGame.player.graveCards.stream()
			.filter(card -> card.art == Art.Nachtgestalt)
			.collect(Collectors.toList())
		);
    }
}