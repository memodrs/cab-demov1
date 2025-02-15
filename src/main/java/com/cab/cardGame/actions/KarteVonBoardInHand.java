package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KarteVonBoardInHand {
    public void execute(CardGame cardGame, Player player, int id, boolean send) {
        cardGame.send(send, player.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_BOARD_IN_HAND);

        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            cardGame.removeCardFromBoard(player, card);
            cardGame.addCardToHand(player, card, true);
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 