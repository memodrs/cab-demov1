package com.cab.cardGame.Factory;

import java.util.HashSet;

import com.cab.cardGame.model.CardState;

public class CardStateFactory {
    public static CardState createCopy(CardState original) {
        CardState copy = original;
        copy.statusSet = new HashSet<>(original.statusSet);
        return copy;
    }
}
