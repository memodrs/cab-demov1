package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KartenTauschenHand {
    private Player player;
    private int idPlayer;
    private int idOponent;

    public KartenTauschenHand(Player player, int idPlayer, int idOponent) {
        this.player = player;
        this.idPlayer = idPlayer;
        this.idOponent = idOponent;
    }

    public void execute(CardGame cardGame) {
        Player oponent = cardGame.getOpOfP(player);
        CardState cardPlayer = cardGame.getCardOfId(idPlayer);
        CardState cardOponent = cardGame.getCardOfId(idOponent);

        if (cardGame.isCardInHand(cardPlayer) && cardGame.isCardInHand(cardOponent)) {
            cardGame.removeCardFromHand(player, cardPlayer);
            cardGame.removeCardFromHand(oponent, cardOponent);
            cardGame.addCardToHand(player, cardOponent, true);
            cardGame.addCardToHand(oponent, cardPlayer, true);
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 