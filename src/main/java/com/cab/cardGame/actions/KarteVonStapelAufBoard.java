package com.cab.cardGame.actions;

import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Messages;

public class KarteVonStapelAufBoard {
    public void execute(CardGame cardGame, Player player, int id, boolean send) {
        cardGame.send(send, player.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_STAPEL_AUF_BOARD);

        CardState card = cardGame.getCardOfId(id);
		if (cardGame.isCardInStapel(card)) {
			cardGame.removeCardFromStapel(player, card);
			cardGame.addCardToBoard(player, card, false);
			cardGame.playSE(2);	
			cardGame.resolve();
		}
    }
}
