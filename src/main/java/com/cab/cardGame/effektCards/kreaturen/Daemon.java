package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Daemon extends CardStateEffekt {

	public Daemon(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {
        decreaseAtkOfArt(cardGame.player, Art.Mensch, cardGame);
        decreaseAtkOfArt(cardGame.player, Art.Tier, cardGame);
        decreaseAtkOfArt(cardGame.oponent, Art.Mensch, cardGame);
        decreaseAtkOfArt(cardGame.oponent, Art.Tier, cardGame);
    }

    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return p.hasArtOnBoard(Art.Mensch) || op.hasArtOnBoard(Art.Mensch) || p.hasArtOnBoard(Art.Tier) || op.hasArtOnBoard(Art.Tier);
    }

    private void decreaseAtkOfArt(Player p, Art art, CardGame cardGame) {
        for (int i = 0; i < p.boardCards.size(); i++) {
            CardState card = p.boardCards.get(i);
            if (card.art == art && !card.isHide) {
                cardGame.karteAngriffVerringern(p.boardCards.get(i).id, 2, true);
            }
        }
    }
}
