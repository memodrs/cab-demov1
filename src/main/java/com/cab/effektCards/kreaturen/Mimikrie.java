package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Mimikrie extends EffektCardState {

	public Mimikrie(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		CardState copyCard = cardGame.getCardOfId(id);

		cardGame.setArtOfCard(cardGame.player, this.id, copyCard.art, true);
		this.life = 0;
		cardGame.karteHeilen(cardGame.player, this.id, copyCard.life, true);
		this.atk = 0;
		cardGame.karteAngriffErhoehen(cardGame.player, this.id, copyCard.atk, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasOpenCardsOnBoard();
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return !card.statusSet.contains(Status.Feuer) && !card.isHide;
	}
}
