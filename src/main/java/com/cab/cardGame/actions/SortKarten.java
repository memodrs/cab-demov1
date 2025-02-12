package com.cab.cardGame.actions;

import java.util.ArrayList;
import java.util.List;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class SortKarten {
    private Player player;
    private int[] reihenfolge;
    private String posName;

    public SortKarten(Player player, int[] reihenfolge, String posName) {
        this.player = player;
        this.reihenfolge = reihenfolge;
        this.posName = posName;
    }

    public void execute(CardGame cardGame) {
        List<CardState> sortedList = new ArrayList<>();

        switch (posName) {
            case "board":
                for (int i = 0; i < reihenfolge.length; i++) {
                    sortedList.add(player.boardCards.get(reihenfolge[i]));
                }
                player.boardCards = sortedList;
                break;
                
            case "stapel":
                for (int i = 0; i < reihenfolge.length; i++) {
                    sortedList.add(player.stapel.get(reihenfolge[i]));
                }
                player.stapel = sortedList;
                break;
                
            case "hand":
                for (int i = 0; i < reihenfolge.length; i++) {
                    sortedList.add(player.handCards.get(reihenfolge[i]));
                }
                player.handCards = sortedList;
                break;
                
            default:
                throw new Error("sortiere Liste, unbekannte posName " + posName);
        }
    }
} 