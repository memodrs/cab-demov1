package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KarteVonHandAufSpellGrave {
    public void execute(CardGame cardGame, Player player, int id, boolean send) {
        cardGame.send(send, player.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_HAND_AUF_SPELL_GRAVE);

        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardInHand(card)) {
            cardGame.removeCardFromHand(player, card);
            cardGame.addCardToSpellGrave(player, card);
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 