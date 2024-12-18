package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Vampir extends CardStateEffekt {	
	public Vampir(Card card) {
		super(card, State.boardState, Trigger.triggerAfterDoAttack, State.ignoreState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteHeilen(this.id, atk, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.boardCards.contains(this);
	}
}