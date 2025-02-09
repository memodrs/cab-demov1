package com.cab.cardGame.Factory;

import java.util.ArrayList;

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
       
        copy.stapel = new ArrayList<>(original.stapel);
        copy.handCards = new ArrayList<>(original.handCards);
        copy.boardCards = new ArrayList<>(original.boardCards);
        copy.graveCards = new ArrayList<>(original.graveCards);
        copy.spellGraveCards = new ArrayList<>(original.spellGraveCards);

        copy.isOnTurn = original.isOnTurn;
        copy.isFirstTurn = original.isFirstTurn;
        copy.inactiveMode = original.inactiveMode;
        copy.numberOfCreatureCanPlayInTurn = original.numberOfCreatureCanPlayInTurn;

        copy.blockEffekteArt = new ArrayList<>(original.blockEffekteArt);
        copy.blockAufrufArtFromHand = new ArrayList<>(original.blockAufrufArtFromHand);
        copy.blockAngriffArt = new ArrayList<>(original.blockAngriffArt);

        return copy;
    }
}