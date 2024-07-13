package com.cab.effektCards;

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
		//TODO Bug: remove und size() vertragen sich natuerlicht nicht
		for (int i = 0; i < p.boardCards.size(); i++) {
			if (p.boardCards.get(i).art == Art.Mensch && !p.boardCards.get(i).isHide) {
				cardGame.karteVomBoardZerstoeren(p, p.boardCards.get(i).id, true, false);
				newAtk++;
			}
		}
		
		Player op = cardGame.getOponentForPlayer(p);
		for (int i = 0; i < op.boardCards.size(); i++) {
			if (op.boardCards.get(i).art == Art.Mensch && !op.boardCards.get(i).isHide) {
				cardGame.karteVomBoardZerstoeren(op, op.boardCards.get(i).id, true, false);
				newAtk++;
			}
		}

		cardGame.karteAngriffErhoehen(p, this.id, newAtk, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.isArtOnBoardOfPlayer(p, Art.Mensch) || cardGame.isArtOnBoardOfPlayer(cardGame.getOponentForPlayer(p), Art.Mensch);
	}
}