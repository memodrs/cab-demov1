package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteVonHandAufSpellGrave {
    private Player player;
    private int id;

    public KarteVonHandAufSpellGrave(Player player, int id) {
        this.player = player;
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardInHand(card)) {
            cardGame.removeCardFromHand(player, card);
            cardGame.addCardToSpellGrave(player, card);
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 