package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Bandit extends CardStateEffekt {

	public Bandit(Card card) {
		super(card, State.boardState, Trigger.triggerKarteWurdeDurchKampfZerstoert, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		cardGame.spielerPunkteAendern(cardGame.player, cardGame.getCardOfId(id).atk, PunkteArt.Leben, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}	