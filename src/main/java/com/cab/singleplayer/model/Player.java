package com.cab.singleplayer.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Player {
    private List<Integer> cards;
    private int coins;

    public boolean hasCoins() {
        return coins > 0;
    }
}
