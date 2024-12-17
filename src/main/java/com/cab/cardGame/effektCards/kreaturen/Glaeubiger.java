package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;;

public class Glaeubiger extends EffektCardState {

	public Glaeubiger(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
        int anzahlMenschenAufDemBoard = 0;
        for (CardState card : cardGame.player.boardCards) {
            if (card.art == Art.Mensch) {
                anzahlMenschenAufDemBoard++;
            }
        }
		cardGame.spielerPunkteAendern(cardGame.player, anzahlMenschenAufDemBoard, PunkteArt.Segen, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}