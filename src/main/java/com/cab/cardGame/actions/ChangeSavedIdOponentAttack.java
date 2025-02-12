package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;

public class ChangeSavedIdOponentAttack {
    private int id;

    public ChangeSavedIdOponentAttack(int id) {
        this.id = id;
    }

    public void execute(CardGame cardGame) {
        cardGame.savedIdOpAttack = id;
    }
} 