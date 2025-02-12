package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteVonBoardInHand {
    private Player player;
    private int id;

    public KarteVonBoardInHand(Player player, int id) {
        this.player = player;
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            cardGame.removeCardFromBoard(player, card);
            cardGame.addCardToHand(player, card, true);
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 