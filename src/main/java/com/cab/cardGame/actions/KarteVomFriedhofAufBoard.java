package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteVomFriedhofAufBoard {
    private Player player;
    private int id;

    public KarteVomFriedhofAufBoard(Player player, int id) {
        this.player = player;
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardInGrave(card)) {
            cardGame.removeCardFromGrave(player, card);
            cardGame.addCardToBoard(player, card, false);
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 