package com.cab.singleplayer;

import java.util.HashMap;
import java.util.Map;

import com.cab.singleplayer.model.Level;
import com.cab.singleplayer.nodes.Coin;
import com.cab.singleplayer.nodes.Node;
import com.cab.singleplayer.nodes.Opfer;
import com.cab.singleplayer.nodes.RandomShop;

import lombok.Getter;

@Getter
public class LevelManager {
    private Map<Integer, Level> levels;

    public LevelManager() {
        Level level1 = new Level(
            new Node(
                new RandomShop(
                    new RandomShop(
                        null,
                        new Opfer(
                            new RandomShop(
                                new Coin(
                                    null,
                                    new Coin(null, null)),
                                 null), 
                            new Coin(
                                null, 
                                new Coin(
                                    new Coin(null, null),
                                    null))) 
                        ), 
                    new RandomShop(
                        new RandomShop(
                            new Coin(
                                null,
                                new Coin(
                                    new Coin(null, null), 
                                    null)), 
                            new Coin(
                                null, 
                                new Coin(
                                    null,
                                    new Coin(null, null)))), 

                        null)
                    ), 

                    new RandomShop(
                        new RandomShop(
                            null,
                            new Opfer(
                                new RandomShop(
                                    new Coin(
                                        null,
                                        new Coin(null, null)),
                                     null), 
                                new Coin(
                                    null, 
                                    new Coin(
                                        new Coin(null, null),
                                        null))) 
                            ), 
                        new RandomShop(
                            new RandomShop(
                                new Coin(
                                    null,
                                    new Coin(
                                        new Coin(null, null), 
                                        null)), 
                                new Coin(
                                    null, 
                                    new Coin(
                                        null,
                                        new Coin(null, null)))), 
    
                            null)
                        )
                    )

            );

        levels = new HashMap<>();
        levels.put(1, level1);
    }
    
}
