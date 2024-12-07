package com.cab.singleplayer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;
import com.cab.singleplayer.level.Level;
import com.cab.singleplayer.nodes.Node;

public class BoardGame {
    private GamePanel gp;
    private final int boardState = 0;
    private final int nodeState = 1;

    private Level level;
    private Node selectedNode;
    private int currentState;

    public BoardGame(GamePanel gp) {
        this.gp = gp;
    }

    public void start(Level level) {
        this.level = level;
        this.selectedNode = level.getNode();
        this.currentState = boardState;
        gp.gameState = gp.boardGameState;
    }

        public void update() {
        if (gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
                if (gp.keyH.leftPressed) {

                } else if (gp.keyH.rightPressed) {

                } else if (gp.keyH.qPressed) {
                    gp.mainMenu.start();
                } else if (gp.keyH.fPressed) {

                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        int startX = Positions.screenHalfWidth;  
        int startY = Positions.tileSize;   
        int levelGap = Positions.tileSize2; // Vertikaler Abstand zwischen Ebenen
        int siblingGap = Positions.tileSize3; // Horizontaler Abstand zwischen Geschwistern
        drawNode(g2, level.getNode(), startX, startY, siblingGap, levelGap);

        g2.setColor(Color.YELLOW);
        g2.setFont(Main.v.brushedFont15);
        g2.drawString("in work Quit with Q...", Positions.tileSize, Positions.tileSize);
    }

    private void drawNode(Graphics2D g2, Node node, int x, int y, int siblingGap, int levelGap) {
        if (node == null) {
            return; 
        }
        
        // Zeichne den aktuellen Knoten (als Kreis)
        int radius = 15; // Radius des Knotens
        g2.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
        
        // Berechne die Position der Kindknoten
        int childY = y + levelGap; // Nächste Ebene
        if (node.getLeftNode() != null) {
            int childX = x - siblingGap; // Links
            // Zeichne Linie zum linken Kind
            g2.draw(new Line2D.Double(x, y, childX, childY));
            // Zeichne linken Kindknoten
            drawNode(g2, node.getLeftNode(), childX, childY, siblingGap / 2, levelGap);
        }
        if (node.getRightNode() != null) {
            int childX = x + siblingGap; // Rechts
            // Zeichne Linie zum rechten Kind
            g2.draw(new Line2D.Double(x, y, childX, childY));
            // Zeichne rechten Kindknoten
            drawNode(g2, node.getRightNode(), childX, childY, siblingGap / 2, levelGap);
        }
    }

    

    
    
    
    
    
}
