package com.cab.singleplayer;

import java.util.HashMap;
import java.util.Map;

import com.cab.singleplayer.model.Level;
import com.cab.singleplayer.nodes.Node;
import com.cab.singleplayer.nodes.Shop;

import lombok.Getter;

@Getter
public class LevelManager {
    private Map<Integer, Level> levels;

    public LevelManager() {
        Level level1 = new Level(
            new Node(
                new Shop(
                    new Shop(null, null), 
                    new Shop(
                        new Shop(null, null), 
                        null)), 
            new Shop(
                new Shop(
                    new Shop(null, null), 
                    new Shop(
                        new Shop(null, null), 
                        new Shop(
                            new Shop(null, null), 
                            null))), 
                null))
        );

        levels = new HashMap<>();
        levels.put(1, level1);
    }
    
}
