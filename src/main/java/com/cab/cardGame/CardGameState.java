package com.cab.cardGame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardGameState {
	private int currentState;

	public boolean isState(int state) {
		return currentState == state;
	}
}

