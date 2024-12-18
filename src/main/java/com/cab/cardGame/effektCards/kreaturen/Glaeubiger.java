package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;;

public class Glaeubiger extends CardStateEffekt {

	public Glaeubiger(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        int anzahlMenschenAufDemBoard = 0;
        for (CardState card : cardGame.player.boardCards) {
            if (card.art == Art.Mensch) {
                anzahlMenschenAufDemBoard++;
            }
        }
		cardGame.spielerPunkteAendern(cardGame.player, anzahlMenschenAufDemBoard, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}