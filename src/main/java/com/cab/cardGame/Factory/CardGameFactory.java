package com.cab.cardGame.Factory;

import java.util.ArrayList;
import java.util.HashMap;

import com.cab.GamePanel;
import com.cab.cardGame.CardGame;

public class CardGameFactory {
    
    public static CardGame createCopy(GamePanel gp, CardGame original) {
        CardGame copy = new CardGame(gp);
        copy.createGame(new ArrayList<>(), true, null);

        copy.cardGameState.setCurrentState(original.cardGameState.getCurrentState());
        copy.cardsOnBoard = new ArrayList<>(original.cardsOnBoard);

        copy.isOnline = original.isOnline;

        if (original.activeEffektCard != null) {
            copy.activeEffektCard = CardStateFactory.createCopy(original.activeEffektCard);
        }

        copy.player = PlayerFactory.createCopy(original.player);
        copy.oponent = PlayerFactory.createCopy(original.oponent);

        copy.continueToDirectAttack = original.continueToDirectAttack;
        copy.continueToAttackPhaseTwo = original.continueToAttackPhaseTwo;
        copy.continueToAttackPhaseThree = original.continueToAttackPhaseThree;

        copy.savedIdPlayerAttack = original.savedIdPlayerAttack;
        copy.savedIdOpAttack = original.savedIdOpAttack;
    
        if (original.optionsToSelect != null) {
            copy.optionsToSelect = new HashMap<>(original.optionsToSelect);
        }
        if (original.optionsCardsToSelect != null) {
            copy.optionsCardsToSelect = new ArrayList<>(original.optionsCardsToSelect);
        }
    
        copy.effektList = new ArrayList<>(original.effektList);

        copy.isResolving = original.isResolving;
        
        return copy;
    }
}