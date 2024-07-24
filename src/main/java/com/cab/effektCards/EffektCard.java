package com.cab.effektCards;

import com.cab.cardGame.Player;

public interface EffektCard {
	public void effekt(Player p, Integer idx);
	public boolean isEffektPossible(Player p);
}
