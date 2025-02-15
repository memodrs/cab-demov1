package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteAngriffVerringern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Einschuechterung extends CardStateSpell {

	public Einschuechterung(Card card) {
		super(card, State.boardState, State.ignoreState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {
		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide) {
				new KarteAngriffVerringern().execute(cardGame, card.id, 2, true);
			}
        }
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.hasOpenCardsOnBoard();
	}
}