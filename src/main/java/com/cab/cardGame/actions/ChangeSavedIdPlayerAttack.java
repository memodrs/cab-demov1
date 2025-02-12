package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;

public class ChangeSavedIdPlayerAttack {
    private int id;

    public ChangeSavedIdPlayerAttack(int id) {
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        cardGame.savedIdPlayerAttack = id;
    }
} 