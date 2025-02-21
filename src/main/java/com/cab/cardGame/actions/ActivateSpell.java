package com.cab.cardGame.actions;

import java.util.Map;

import com.cab.card.Art;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;
import com.cab.configs.Messages;

public class ActivateSpell extends Action {
    public void execute(CardGame cardGame, Player player, int id, boolean send) {
        cardGame.send(send, player.isPlayer, id, null, null, null, null, null, null, Messages.ACTIVATE_SPELL);

        CardState card = cardGame.getCardOfId(id);

        if (cardGame.isCardInHand(card)) {
            Map<Art, PunkteArt> artToPunkteArt = Map.of(
                Art.Segen, PunkteArt.Segen,
                Art.Fluch, PunkteArt.Fluch
            );
            
            PunkteArt punkteArt = artToPunkteArt.get(card.art);
            
            if (punkteArt == null) {
                throw new IllegalArgumentException("ActivateSpell: Invalid card art: " + card.art);
            }

            new SpielerPunkteAendern().execute(cardGame, player, -card.defaultCard.getKosten(), punkteArt, false);

            cardGame.removeCardFromHand(player, card);
            cardGame.addCardToSpellGrave(player, card);

            cardGame.addEffektToList(id, card.triggerState, -1);
            cardGame.resolve();

            cardGame.gp.playSE(2);
        }

    }
}
