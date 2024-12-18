package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Hydra extends CardStateEffekt {

	public Hydra(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
        cardGame.karteAngriffErhoehen(this.id, atk, true);
    }
	
	public boolean isEffektPossible(Player p) {
		return p.boardCards.contains(this);
	}
}
