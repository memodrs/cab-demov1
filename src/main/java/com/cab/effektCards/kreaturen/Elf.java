package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Elf extends EffektCardState {

	public Elf(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		int leben = cardGame.getCardOfId(id).life;
		for (CardState card : cardGame.player.boardCards) {
			if (card.art == Art.Fabelwesen) {
				cardGame.karteHeilen(card.id, leben, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		Player op = cardGame.getOpOfP(p);
		return op.hasOpenCardsOnBoard();
	}

	
	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}

}