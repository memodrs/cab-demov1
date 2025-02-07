package com.cab.singleplayer;

import java.util.HashMap;
import java.util.Map;

import com.cab.singleplayer.model.Level;
import com.cab.singleplayer.nodes.Coin;
import com.cab.singleplayer.nodes.Node;
import com.cab.singleplayer.nodes.RandomShop;

import lombok.Getter;

@Getter
public class LevelManager {
    private Map<Integer, Level> levels;

    public LevelManager() {
        Level level1 = new Level(
            new Node(
                new RandomShop(
                    new RandomShop(null, null), 
                    new RandomShop(
                        new RandomShop(null, null), 
                        null)), 
            new Coin(
                new RandomShop(
                    new RandomShop(null, null), 
                    new Coin(
                        new RandomShop(null, null), 
                        new Coin(
                            new RandomShop(null, null), 
                            null))), 
                null))
        );

        levels = new HashMap<>();
        levels.put(1, level1);
    }
    
}
