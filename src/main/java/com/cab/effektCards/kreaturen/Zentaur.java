package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;

public class Zentaur extends EffektCardState {

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
