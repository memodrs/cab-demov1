package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Zentaur extends CardStateEffekt {

    public Zentaur(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {
        this.hasAttackOnTurn = false;
		cardGame.spielerPunkteAendern(cardGame.player, -1, PunkteArt.Segen, true);
	}

	public boolean isEffektPossible(Player p) {
		return !this.isEffectActivateInTurn && this.hasAttackOnTurn;
	};
}
