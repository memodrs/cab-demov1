package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class SpielerPunkteAendern {
    private Player player;
    private int punkte;
    private PunkteArt art;

    public SpielerPunkteAendern(Player player, int punkte, PunkteArt art) {
        this.player = player;
        this.punkte = punkte;
        this.art = art;
    }

    public void execute(CardGame cardGame) {
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