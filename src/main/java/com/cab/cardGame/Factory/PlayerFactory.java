package com.cab.cardGame.Factory;

import java.util.ArrayList;
import java.util.List;

import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;

public class PlayerFactory {
    public static Player createCopy(Player original) {
        if (original == null) return null;
        
        Player copy = new Player();

        copy.isPlayer = original.isPlayer;
        copy.isKI = original.isKI;
        copy.lifeCounter = original.lifeCounter;
        copy.segenCounter = original.segenCounter;
        copy.fluchCounter = original.fluchCounter;
       
        copy.stapel = new ArrayList<>();
        copy.handCards = new ArrayList<>();
        copy.boardCards = new ArrayList<>();
        copy.graveCards = new ArrayList<>();
        copy.spellGraveCards = new ArrayList<>();

        copyCards(copy.stapel, original.stapel);
        copyCards(copy.handCards, original.handCards);
        copyCards(copy.boardCards, original.boardCards);
        copyCards(copy.graveCards, original.graveCards);
        copyCards(copy.spellGraveCards, original.spellGraveCards);

        copy.isOnTurn = original.isOnTurn;
        copy.isFirstTurn = original.isFirstTurn;
        copy.inactiveMode = original.inactiveMode;
        copy.numberOfCreatureCanPlayInTurn = original.numberOfCreatureCanPlayInTurn;

        copy.blockEffekteArt = new ArrayList<>(original.blockEffekteArt);
        copy.blockAufrufArtFromHand = new ArrayList<>(original.blockAufrufArtFromHand);
        copy.blockAngriffArt = new ArrayList<>(original.blockAngriffArt);

        return copy;
    }

    private static void copyCards(List<CardState> copyList, List<CardState> originalList) {
        for (CardState cardState : originalList) {
            copyList.add(CardStateFactory.createCopy(cardState));
        }
    }
}