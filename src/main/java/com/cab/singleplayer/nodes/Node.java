package com.cab.singleplayer.nodes;

import java.awt.Graphics2D;

import com.cab.singleplayer.Singleplayer;

import lombok.Getter;

@Getter
public class Node {
    protected Singleplayer singleplayer;
    protected Node left;
    protected Node right;

    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public void init(Singleplayer singleplayer) {
        this.singleplayer = singleplayer;
    };

    public void update() {};
    public void draw(Graphics2D g2) {};

    public boolean hasLeft() {
        return left != null;
    } 

    public boolean hasRight() {
        return right != null;
    }
}
