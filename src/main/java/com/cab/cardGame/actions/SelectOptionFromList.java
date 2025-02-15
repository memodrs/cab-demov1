package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.configs.Messages;

public class SelectOptionFromList {
    public void execute(CardGame cardGame, String selectedOption, boolean send) {
        cardGame.send(send, null, null, null, null, null, null, null, selectedOption, Messages.SELECT_OPTION_FROM_LIST);

        cardGame.cd.showSelectedOption(selectedOption);
    }
} 