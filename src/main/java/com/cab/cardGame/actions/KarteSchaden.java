package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KarteSchaden {
    public void execute(CardGame cardGame, Player player, int id, int schaden, boolean send, boolean ignoreResolve) {
        cardGame.send(send, player.isPlayer, id, schaden, null, null, null, null, null, Messages.KARTE_SCHADEN);

        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            if (card.life <= schaden) {
                new KarteVomBoardInFriedhof().execute(cardGame, player, id, false, true);
            } else {
                card.life = card.life - schaden;
            }
            if (!ignoreResolve) {
                cardGame.resolve();
            }
        }
    }
} 