package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KartenTauschenHand {
    public void execute(CardGame cardGame, Player player, int idPlayer, int idOponent, boolean send) {
        cardGame.send(send, player.isPlayer, idPlayer, idOponent, null, null, null, null, null, Messages.KARTEN_TAUSCHEN_HAND);
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