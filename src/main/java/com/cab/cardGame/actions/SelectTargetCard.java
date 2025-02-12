package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;

public class SelectTargetCard {
    private int id;

    public SelectTargetCard(int id) {
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        cardGame.cd.showCardTargeted(cardGame.getCardOfId(id));
    }
} 