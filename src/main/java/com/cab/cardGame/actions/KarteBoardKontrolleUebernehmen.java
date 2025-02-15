package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KarteBoardKontrolleUebernehmen {
    public void execute(CardGame cardGame, Player player, int id, boolean send) {
        cardGame.send(send, player.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_BOARD_KONTROLLE_UEBERNEHMEN);

        Player oponent = cardGame.getOpOfP(player);
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            oponent.boardCards.remove(card);
            player.boardCards.add(card);
            cardGame.updateBlocks();
            cardGame.gp.playSE(2);
            cardGame.resolve();
        }
    }
} 