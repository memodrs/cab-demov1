package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Wissenschaftler extends CardStateEffekt {

	public Wissenschaftler(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVonStapelAufHand(cardGame.player, id, true);
		cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.stapel.size() >= 3;
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		Player p = cardGame.player;
		cardGame.optionsCardsToSelect.add(p.stapel.get(p.stapel.size() - 1));
		cardGame.optionsCardsToSelect.add(p.stapel.get(p.stapel.size() - 2));
		cardGame.optionsCardsToSelect.add(p.stapel.get(p.stapel.size() - 3));
    }
}
