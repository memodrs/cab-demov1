package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Ritter extends EffektCardState {

	public Ritter(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		cardGame.karteAngriffErhoehen(p, this.id, this.defaultCard.atk, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.isArtOnBoardOfPlayer(cardGame.getOpOfP(p), Art.Fabelwesen);
	}

	public void removeBeforeAttackEffekt(Player p) {
		if (isEffectActivateInTurn) {
			cardGame.karteAngriffVerringern(p, this.id, this.defaultCard.atk, false);
		}
	}
}