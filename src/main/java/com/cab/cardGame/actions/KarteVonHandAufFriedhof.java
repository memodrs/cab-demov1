package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KarteVonHandAufFriedhof {
    public void execute(CardGame cardGame, Player player, int id, boolean send) {
        cardGame.send(send, player.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_HAND_AUF_FRIEDHOF);

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