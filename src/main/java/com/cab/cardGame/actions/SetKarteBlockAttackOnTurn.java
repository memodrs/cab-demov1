package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;

public class SetKarteBlockAttackOnTurn {
    private int id;
    private boolean isBlock;

    public SetKarteBlockAttackOnTurn(int id, boolean isBlock) {
        this.id = id;
        this.isBlock = isBlock;
    }

    public void execute(CardGame cardGame) {
        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            card.blockAttackOnTurn = isBlock;
            cardGame.resolve();
        }
    }
} 