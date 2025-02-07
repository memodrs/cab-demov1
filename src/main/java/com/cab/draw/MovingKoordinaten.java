package com.cab.draw;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class MovingKoordinaten {
    private int x, y;

    public boolean isOnZielX(int xZiel) {
        return x >= xZiel;
    }

    public boolean isOnZielY(int yZiel) {
        return y >= yZiel;
    }
}
