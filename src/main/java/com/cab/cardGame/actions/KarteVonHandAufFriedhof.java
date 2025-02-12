package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class KarteVonHandAufFriedhof {
    private Player player;
    private int id;

    public KarteVonHandAufFriedhof(Player player, int id) {
        this.player = player;
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardInHand(card)) {
            cardGame.removeCardFromHand(player, card);
            if (card.defaultCard.isSpell()) {
                cardGame.addCardToSpellGrave(player, card);
            } else {
                cardGame.addCardToGrave(player, card, true);
            }
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 