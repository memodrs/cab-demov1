package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class SetUpDirekterAngriff {
    public void execute(CardGame cardGame, Player player, int idx, boolean send) {
        cardGame.send(send, player.isPlayer, idx, null, null, null, null, null, null, Messages.SETUP_DIREKTER_ANGRIFF);

        CardState card = player.boardCards.get(idx);
        cardGame.savedIdPlayerAttack = card.id;

        cardGame.addEffektToList(card.id, Trigger.triggerBeforeDirekterAngriff, -1);
        cardGame.addEffekteToList(cardGame.getOpOfP(player).handCards, 
            Trigger.triggerOnHandBeforeDamageDirekterAngriff, card.id);

        if (cardGame.effektList.size() > 0) {
            cardGame.continueToDirectAttack = true;
            cardGame.resolve();
        } else {
            new DirekterAngriff().execute(cardGame, player, card.id, false);
        }
    }
} 