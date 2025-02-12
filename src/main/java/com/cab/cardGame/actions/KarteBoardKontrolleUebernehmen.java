package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteBoardKontrolleUebernehmen {
    private Player player;
    private int opId;

    public KarteBoardKontrolleUebernehmen(Player player, int opId) {
        this.player = player;
        this.opId = opId;
    }

    public void execute(CardGame cardGame) {
        Player oponent = cardGame.getOpOfP(player);
        CardState card = cardGame.getCardOfId(opId);

        if (cardGame.isCardOnBoard(card)) {
            oponent.boardCards.remove(card);
            player.boardCards.add(card);
            cardGame.updateBlocks();
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 