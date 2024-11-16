package com.cab.effektCards;

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

	public void effekt(Player p, Integer id) {
		int leben = cardGame.getCardOfId(id).life;
		for (CardState card : p.boardCards) {
			if (card.art == Art.Fabelwesen) {
				cardGame.karteHeilen(p, card.id, leben, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		Player op = cardGame.getOpOfP(p);
		return op.boardCards.size() > 0 && cardGame.hasPlayerOpenCardsOnBoard(op);
	}

	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}

}