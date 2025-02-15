package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufStapel;
import com.cab.cardGame.actions.KartenZiehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Kessel extends CardStateSpell {

	public Kessel(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {
        int size = cardGame.player.handCards.size();

        for (int i = 0; i < size; i++) {
			new KarteVonHandAufStapel().execute(cardGame, cardGame.player, cardGame.player.handCards.get(0).id, true);
        }
        cardGame.kartenMischen(cardGame.player, cardGame.player.stapel, true);
		new KartenZiehen().execute(cardGame, cardGame.player, size, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.handCards.size() > 1;
	}
}