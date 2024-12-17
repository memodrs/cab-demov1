package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Ritter extends EffektCardState {

	public Ritter(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.karteAngriffErhoehen(this.id, this.defaultCard.getAtk(), true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasArtOnBoard(Art.Fabelwesen);
	}

	public void removeBeforeAttackEffekt(Player p) {
		if (isEffectActivateInTurn) {
			cardGame.karteAngriffVerringern(this.id, this.defaultCard.getAtk(), false);
		}
	}
}