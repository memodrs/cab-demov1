package com.cab.cardGame.effektCards.kreaturen;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonStapelAufHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;




public class Wissenschaftler extends CardStateEffekt {

	public Wissenschaftler(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonStapelAufHand().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
		cardGame.kartenMischen(cardGame.getOwnerOfCard(this), cardGame.getOwnerOfCard(this).stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).stapel.size() >= 3;
	}


	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		Player p = cardGame.getOwnerOfCard(this);
		return new ArrayList<>(p.stapel.subList(p.stapel.size() - 3, p.stapel.size()));
    }
}
