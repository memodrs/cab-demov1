package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class S_Wandler extends EffektCardState implements EffektCard {

	public S_Wandler(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		cardGame.setArtOfCardOnBoard(p, id, Art.Mensch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.isArtOnBoardOfPlayer(p, Art.Fabelwesen) || cardGame.isArtOnBoardOfPlayer(p, Art.Nachtgestalt) || cardGame.isArtOnBoardOfPlayer(p, Art.Tier) || cardGame.isArtOnBoardOfPlayer(p, Art.Unbekannt);
	}

	public boolean isCardValidForSelection(CardState card) {
		return card.art != Art.Mensch && !card.isHide;
	}
}