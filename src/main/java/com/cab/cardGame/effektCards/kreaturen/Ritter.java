package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Ritter extends CardStateEffekt {

	public Ritter(Card card) {
		super(card, State.boardState, Trigger.triggerBeforeKarteAngreift, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteAngriffErhoehen(this.id, this.defaultCard.getAtk(), true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.hasArtOnBoard(Art.Fabelwesen);
	}

	@Override
	public void removeBeforeAttackEffekt(CardGame cardGame) {
		cardGame.karteAngriffVerringern(this.id, this.defaultCard.getAtk(), false);
	}
}