package com.cab.cardGame.Factory;

import java.util.HashSet;

import com.cab.cardGame.EffektManager;
import com.cab.cardGame.model.CardState;

public class CardStateFactory {
    public static CardState createCopy(CardState original) {
        CardState copy = EffektManager.getCardStateForCard(original.defaultCard);

        copy.id = original.id;
        copy.atk = original.atk;
        copy.life = original.life;
        copy.art = original.art;
        
        copy.isEffectActivate = original.isEffectActivate;
        copy.isEffectActivateInTurn = original.isEffectActivateInTurn;

        copy.isEffekt = original.isEffekt;
        copy.selectState = original.selectState;
        copy.nextStateForPlayer = original.nextStateForPlayer;
        copy.triggerState = original.triggerState;

        copy.wasPlayedInTurn = original.wasPlayedInTurn;
        copy.isHide = original.isHide;
        copy.hasAttackOnTurn = original.hasAttackOnTurn;
        copy.blockAttackOnTurn = original.blockAttackOnTurn;

        copy.statusSet = new HashSet<>(original.statusSet);
        return copy;
    }
}
