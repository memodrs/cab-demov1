package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteAngriffErhoehen;
import com.cab.cardGame.actions.KarteAngriffVerringern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Ritter extends CardStateEffekt {

	public Ritter(Card card) {
		super(card, State.boardState, Trigger.triggerBeforeKarteAngreift, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteAngriffErhoehen().execute(cardGame, this.id, this.defaultCard.getAtk(), true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOpOfCard(this).hasArtOnBoard(Art.Fabelwesen);
	}

	@Override
	public void removeBeforeAttackEffekt(CardGame cardGame) {
		new KarteAngriffVerringern().execute(cardGame, this.id, this.defaultCard.getAtk(), false);
	}
}