package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.configs.Messages;

public class SetKarteBlockAttackOnTurn {
    public void execute(CardGame cardGame, int id, boolean isBlock, boolean send) {
        cardGame.send(send, null, id, null, isBlock, null, null, null, null, Messages.SET_KARTE_BLOCK_ATTACK_ON_TURN);

        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            card.blockAttackOnTurn = isBlock;
            cardGame.resolve();
        }
    }
} 