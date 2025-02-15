package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Roboto extends CardStateEffekt {

	public Roboto(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		/*
		int newAtk = 0;
		List<Integer> idsToRemove = new ArrayList<Integer>();

		for (int i = 0; i < cardGame.getOwnerOfCard(this).boardCards.size(); i++) {
			if (cardGame.getOwnerOfCard(this).boardCards.get(i).art == Art.Mensch && !cardGame.getOwnerOfCard(this).boardCards.get(i).isHide) {
				idsToRemove.add(cardGame.getOwnerOfCard(this).boardCards.get(i).id);
			}
		}

		for (int i = 0; i < idsToRemove.size(); i++) {
			cardGame.kreaturVomBoardZerstoeren(p, idsToRemove.get(i), true, false);
			newAtk++;
		}

		idsToRemove = new ArrayList<>();
		Player op = cardGame.getOpOfP(p);

		for (int i = 0; i < cardGame.getOpOfCard(this).boardCards.size(); i++) {
			if (cardGame.getOpOfCard(this).boardCards.get(i).art == Art.Mensch && !cardGame.getOpOfCard(this).boardCards.get(i).isHide) {
				idsToRemove.add(cardGame.getOpOfCard(this).boardCards.get(i).id);
			}
		}

		for (int i = 0; i < idsToRemove.size(); i++) {
			cardGame.kreaturVomBoardZerstoeren(op, idsToRemove.get(i), true, false);
			newAtk++;
		}

		cardGame.karteAngriffErhoehen(p, this.id, newAtk, true);

		*/
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasArtOnBoard(Art.Mensch) || cardGame.getOpOfCard(this).hasArtOnBoard(Art.Mensch);
	}
}