package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;
import com.cab.configs.Messages;

public class SpielerPunkteAendern {
    public void execute(CardGame cardGame, Player player, int punkte, PunkteArt art, boolean send) {
        cardGame.send(send, player.isPlayer, punkte, null, null, null, null, null, art.toString(), Messages.SPIELER_PUNKTE_AENDERN);

        if (art == PunkteArt.Fluch) {
            player.fluchCounter += punkte;
        } else if (art == PunkteArt.Segen) {
            player.segenCounter += punkte;
        } else if (art == PunkteArt.Leben) {
            player.lifeCounter += punkte;
            if (player.lifeCounter <= 0) {
                player.lifeCounter = 0;
                cardGame.switchState(State.gameFinishedState);
            }
        } else {
            throw new Error("Unbekannte Punkte Art " + art);
        }
    }
} 