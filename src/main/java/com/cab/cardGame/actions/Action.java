package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Action {
    private Action nextAction;

    public void execute(CardGame cardGame) {}
}
