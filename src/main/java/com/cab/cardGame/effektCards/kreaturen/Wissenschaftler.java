package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;



public class Wissenschaftler extends EffektCardState {

	public Wissenschaftler(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Integer id) {
		cardGame.karteVonStapelAufHand(cardGame.player, id, true);
		cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p) {
		return p.stapel.size() >= 3;
	}


	@Override
	public void setUpOptionsToSelect() {
        super.setUpOptionsToSelect();
		Player p = cardGame.player;
		cardGame.optionsCardsToSelect.add(p.stapel.get(p.stapel.size() - 1));
		cardGame.optionsCardsToSelect.add(p.stapel.get(p.stapel.size() - 2));
		cardGame.optionsCardsToSelect.add(p.stapel.get(p.stapel.size() - 3));
    }
}
