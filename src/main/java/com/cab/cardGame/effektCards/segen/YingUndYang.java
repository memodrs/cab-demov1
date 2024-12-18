package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class YingUndYang extends CardStateSpell {

	public YingUndYang(Card card) {
		super(card, State.boardState, State.effektSelectOwnBoardState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        cardGame.karteVomFriedhofAufBoard(cardGame.player, id, true);
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
		return  p.isBoardEmpty() && p.hasGraveCards() && op.boardCards.size() == 1;
    }
}