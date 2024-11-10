package com.cab.effektCards;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Roboto extends EffektCardState implements EffektCard {

	public Roboto(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		int newAtk = 0;
		List<Integer> idsToRemove = new ArrayList<Integer>();

		for (int i = 0; i < p.boardCards.size(); i++) {
			if (p.boardCards.get(i).art == Art.Mensch && !p.boardCards.get(i).isHide) {
				idsToRemove.add(p.boardCards.get(i).id);
			}
		}

		for (int i = 0; i < idsToRemove.size(); i++) {
			cardGame.kreaturVomBoardZerstoeren(p, idsToRemove.get(i), true, false);
			newAtk++;
		}

		idsToRemove = new ArrayList<>();
		Player op = cardGame.getOpOfP(p);

		for (int i = 0; i < op.boardCards.size(); i++) {
			if (op.boardCards.get(i).art == Art.Mensch && !op.boardCards.get(i).isHide) {
				idsToRemove.add(op.boardCards.get(i).id);
			}
		}

		for (int i = 0; i < idsToRemove.size(); i++) {
			cardGame.kreaturVomBoardZerstoeren(op, idsToRemove.get(i), true, false);
			newAtk++;
		}

		cardGame.karteAngriffErhoehen(p, this.id, newAtk, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.isArtOnBoardOfPlayer(p, Art.Mensch) || cardGame.isArtOnBoardOfPlayer(cardGame.getOpOfP(p), Art.Mensch);
	}
}