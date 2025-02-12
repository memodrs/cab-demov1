package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;

public class SelectOptionFromList {
    private String selectedOption;

    public SelectOptionFromList(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public void execute(CardGame cardGame) {
        cardGame.cd.showSelectedOption(selectedOption);
    }
} 