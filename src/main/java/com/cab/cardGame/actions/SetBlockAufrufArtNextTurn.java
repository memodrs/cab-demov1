package com.cab.cardGame.actions;

import com.cab.card.Art;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.Player;

public class SetBlockAufrufArtNextTurn {
    private Player player;
    private boolean isBlock;
    private Art art;

    public SetBlockAufrufArtNextTurn(Player player, boolean isBlock, Art art) {
        this.player = player;
        this.isBlock = isBlock;
        this.art = art;
    }

    public void execute(CardGame cardGame) {
        if (isBlock) {
            player.blockAufrufArtFromHand.add(art);
        } else {
            player.blockAufrufArtFromHand.remove(art);
        }
        cardGame.resolve();
    }
} 