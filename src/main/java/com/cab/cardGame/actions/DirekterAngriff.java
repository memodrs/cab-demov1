package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;
import com.cab.configs.Messages;

public class DirekterAngriff {
    public void execute(CardGame cardGame, Player player, int id, boolean send) {
        cardGame.send(send, player.isPlayer, id, null, null, null, null, null, null, Messages.DIREKTER_ANGRIFF);
        cardGame.continueToDirectAttack = false;
        CardState card = cardGame.getCardOfId(id);
        cardGame.cd.showDirectAttack(card);

        card.hasAttackOnTurn = true;
        new SpielerPunkteAendern().execute(cardGame, cardGame.getOpOfP(player), -card.atk, PunkteArt.Leben, false);

        if (cardGame.getOpOfP(player).lifeCounter > 0) {
            cardGame.addEffektToList(card.id, Trigger.triggerDirekterAngriff, -1);
            cardGame.addEffektToList(card.id, Trigger.triggerhatAngegriffen, -1);
            cardGame.addEffekteToList(cardGame.getOpOfP(player).handCards, Trigger.triggerOnHandDamageDirekterAngriff, card.id);
            cardGame.switchState(State.boardState);
            cardGame.resolve();
        }
    }
} 