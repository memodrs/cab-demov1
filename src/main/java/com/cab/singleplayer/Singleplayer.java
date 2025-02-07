package com.cab.singleplayer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import com.cab.GamePanel;
import com.cab.singleplayer.model.Level;
import com.cab.singleplayer.model.Player;
import com.cab.singleplayer.nodes.Node;
import com.cab.singleplayer.nodes.RandomShop;
import com.cab.states.GameState;

public class Singleplayer extends GameState {
    public GamePanel gp;
    
    public Player p;

    private int state;
    private int selectedIdx;
    private final int boardState = 0;
    private final int nodeState = 1;

    private LevelManager levelManager;
    private Level selectedLevel;
    private Node activeNode;



    public Singleplayer(GamePanel gp) {
        this.gp = gp;
        this.p = new Player();
    }
    
    public void start() {

        p.setCoins(4);
        p.setCards(new ArrayList<>());
        p.getCards().add(4);
        p.getCards().add(5);
        p.getCards().add(6);
        p.getCards().add(7);
        p.getCards().add(8);
        p.getCards().add(12);

        levelManager = new LevelManager();
        selectedIdx = 0;
        state = boardState;
        selectedLevel = levelManager.getLevels().get(1);
        activeNode = selectedLevel.getStartNode();
        
        gp.switchState(gp.singlePlayerState);
    }

    public void quitNode() {
        if (activeNode.hasLeft()) {
            selectedIdx = 0;
        } else if (activeNode.hasRight()) {
            selectedIdx = 1;
        } else {
            selectedIdx = -1;
        }
        state = boardState;
    }

    private void switchToNextNode() {
        activeNode = getNodeForSelectedIdx();
        activeNode.init(this);
        state = nodeState;
    }

    private Node getNodeForSelectedIdx() {
        if (selectedIdx == 0) {
            return activeNode.getLeft();
        } else if (selectedIdx == 1) {
            return activeNode.getRight();
        } throw new Error("switchToNextNode ungültiger Index");
    }


    @Override
    public void update() {
        if (state == boardState) {
            if (gp.keyH.leftPressed) {
                if (activeNode.hasLeft()) {
                    selectedIdx = 0;
                }
            } else if (gp.keyH.rightPressed) {
                if (activeNode.hasRight()) {
                    selectedIdx = 1;
                }
            } else if (gp.keyH.fPressed) {
                switchToNextNode();
            } else if (gp.keyH.qPressed) {
                gp.switchState(gp.mainMenuState);
            }
        } else if (state == nodeState) {
            activeNode.update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (state == boardState) {
            drawNode(g2, selectedLevel.getStartNode(), gp.p(16), gp.p(20), gp.p(6), gp.p(3));
            gp.drawLib.drawGButton(g2, "stapel");
        } else if (state == nodeState) {
            activeNode.draw(g2);
        }

        g2.drawImage(gp.imageLoader.iconCoind, gp.p(1), gp.p(0.8), gp.p(0.8), gp.p(0.8), null);
        g2.setColor(Color.WHITE);
        g2.setFont(gp.font(30));
        g2.drawString("" + p.getCoins(), gp.p(2), gp.p(1.4));
    }

    private void drawNode(Graphics2D g2, Node node, int x, int y, int siblingGap, int levelGap) {
        if (node == null) {
            return; 
        }
        
        // Zeichne den aktuellen Knoten (als Kreis)
        int radius = 20; // Radius des Knotens
        if (activeNode == node) {
            g2.setColor(Color.RED);
        } else {
            g2.setColor(Color.GRAY);
        }
        g2.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));

        // Berechne die Position der Kindknoten (nach oben verschoben)
        int childY = y - levelGap; // Nächste Ebene nach oben
        if (node.getLeft() != null) {
            if (selectedIdx == 0 && node == activeNode) {
                g2.setColor(Color.ORANGE);
            } else {
                g2.setColor(Color.WHITE);
            }
            int childX = x - siblingGap; // Links
            // Zeichne Linie zum linken Kind (nach oben)
            g2.draw(new Line2D.Double(x, y - radius, childX, childY + radius));
            // Zeichne linken Kindknoten
            drawNode(g2, node.getLeft(), childX, childY, siblingGap / 2, levelGap);
            gp.drawLib.drawHover(g2, childX - gp.p(0.5), childY - gp.p(0.5), gp.p(1), gp.p(1), selectedIdx == 0 && node == activeNode);
        }
        if (node.getRight() != null) {
            if (selectedIdx == 1 && node == activeNode) {
                g2.setColor(Color.ORANGE);
            } else {
                g2.setColor(Color.WHITE);
            }

            int childX = x + siblingGap; // Rechts
            // Zeichne Linie zum rechten Kind (nach oben)
            g2.draw(new Line2D.Double(x, y - radius, childX, childY + radius));
            // Zeichne rechten Kindknoten
            drawNode(g2, node.getRight(), childX, childY, siblingGap / 2, levelGap);
            gp.drawLib.drawHover(g2, childX - gp.p(0.5), childY - gp.p(0.5), gp.p(1), gp.p(1), selectedIdx == 1 && node == activeNode);
        }
    }
}
