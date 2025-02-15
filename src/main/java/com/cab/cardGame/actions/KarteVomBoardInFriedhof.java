package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KarteVomBoardInFriedhof {
    public void execute(CardGame cardGame, Player player, int id, boolean send, boolean ignoreResolve) {
        cardGame.send(send, player.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VOM_BOARD_IN_FRIEDHOF);

        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardOnBoard(card)) {
            cardGame.removeCardFromBoard(player, card);
            cardGame.addCardToGrave(player, card, true);

            cardGame.addEffektToList(card.id, Trigger.triggerAfterDestroyed, -1);

            cardGame.addEffekteToList(player.boardCards, Trigger.triggerOnZerstoertKreaturZerstoert, card.id);
            cardGame.addEffekteToList(player.boardCards, Trigger.triggerOnZerstoertPlayerKreaturZerstoert, card.id);

            cardGame.addEffekteToList(cardGame.getOpOfP(player).boardCards, Trigger.triggerOnZerstoertKreaturZerstoert, card.id);
            cardGame.addEffekteToList(cardGame.getOpOfP(player).boardCards, Trigger.triggerOnZerstoertOponentKreaturZerstoert, card.id);

            if (player.isPlayer) {
                cardGame.switchState(State.graveState);
            } else {
                cardGame.switchState(State.graveOponentState);
            }

            if (!ignoreResolve) {
                cardGame.resolve();
            }
        }
    }
} 