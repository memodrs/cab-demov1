package com.cab.singleplayer.nodes;

import lombok.Getter;

@Getter
public class Node {
    private Node leftNode;
    private Node rightNode;

    public Node(Node leftNode, Node rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
}
