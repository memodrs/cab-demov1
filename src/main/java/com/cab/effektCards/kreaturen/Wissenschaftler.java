package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Wissenschaftler extends EffektCardState {

	public Wissenschaftler(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	@Override
	public void effekt(Player p, Integer id) {
		cardGame.karteVonStapelAufDieHand(p, id, true);
		cardGame.kartenMischen(p, p.stapel, true);
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