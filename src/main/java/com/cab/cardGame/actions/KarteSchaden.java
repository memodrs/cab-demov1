package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteSchaden {
    private Player player;
    private int id;
    private int schaden;
    private boolean ignoreResolve;

    public KarteSchaden(Player player, int id, int schaden, boolean ignoreResolve) {
        this.player = player;
        this.id = id;
        this.schaden = schaden;
        this.ignoreResolve = ignoreResolve;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            if (card.life <= schaden) {
                cardGame.karteVomBoardInFriedhof(player, id, false, true);
            } else {
                card.life = card.life - schaden;
            }
            if (!ignoreResolve) {
                cardGame.resolve();
            }
        }
    }
} 