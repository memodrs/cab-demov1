package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Daemon extends CardStateEffekt {

	public Daemon(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    public void effekt(Integer id) {
        decreaseAtkOfArt(cardGame.player, Art.Mensch);
        decreaseAtkOfArt(cardGame.player, Art.Tier);
        decreaseAtkOfArt(cardGame.oponent, Art.Mensch);
        decreaseAtkOfArt(cardGame.oponent, Art.Tier);
    }

    public boolean isEffektPossible(Player p) {
        return p.hasArtOnBoard(Art.Mensch) || cardGame.getOpOfP(p).hasArtOnBoard(Art.Mensch) || p.hasArtOnBoard(Art.Tier) || cardGame.getOpOfP(p).hasArtOnBoard(Art.Tier);
    }

    private void decreaseAtkOfArt(Player p, Art art) {
        for (int i = 0; i < p.boardCards.size(); i++) {
            CardState card = p.boardCards.get(i);
            if (card.art == art && !card.isHide) {
                cardGame.karteAngriffVerringern(p.boardCards.get(i).id, 2, true);
            }
        }
    }
}
