package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Nix extends CardStateEffekt {
  
  public Nix(Card card) {
		super(card, State.ignoreState, Trigger.triggerPermanent, State.ignoreState);
	}

		@Override
    public void setBlock(Player p, Player op) {
      op.blockAngriffMenschen = true;
    }
}
