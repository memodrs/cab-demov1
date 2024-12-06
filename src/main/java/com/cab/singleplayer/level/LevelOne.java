package com.cab.singleplayer.level;

import com.cab.singleplayer.nodes.Node;

public class LevelOne extends Level {

    public LevelOne() {
        super();
        this.node = 
        new Node(
            new Node(
                null, 
                new Node(
                    null, 
                    null)
            ), 
            new Node(
                new Node(
                    null, 
                    null), 
                new Node(
                    null, 
                    null)
            )
        );
    }
}
