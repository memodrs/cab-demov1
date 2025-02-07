package com.cab.singleplayer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.util.ArrayList;

import com.cab.GamePanel;
import com.cab.configs.Colors;
import com.cab.singleplayer.model.Level;
import com.cab.singleplayer.model.Player;
import com.cab.singleplayer.nodes.Node;
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
            } else if (gp.keyH.enterPressed) {
                levelManager = new LevelManager();
                selectedIdx = 0;
                state = boardState;
                selectedLevel = levelManager.getLevels().get(1);
                activeNode = selectedLevel.getStartNode();
            }
        } else if (state == nodeState) {
            activeNode.update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (state == boardState) {
            g2.drawImage(gp.imageLoader.iconSingleplayerBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
            g2.setColor(Colors.transparentBlack);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            drawNode(g2, selectedLevel.getStartNode(), gp.p(16), gp.p(19), gp.p(8), gp.p(2.2));
            gp.drawLib.drawGButton(g2, "stapel");

        } else if (state == nodeState) {
            activeNode.draw(g2);
        }

        g2.drawImage(gp.imageLoader.iconCoind, gp.p(1), gp.p(0.8), gp.p(0.8), gp.p(0.8), null);
        g2.setColor(Color.WHITE);
        g2.setFont(gp.font(30));
        g2.drawString("" + p.getCoins(), gp.p(2), gp.p(1.4));

        for (int i = 0; i < p.getCards().size(); i++) {
            gp.drawLib.drawCardStandardSize(g2, gp.cardLoader.getCard(p.getCards().get(i)), gp.p(37), gp.p(0.5) + i * gp.p(1.5), false, false);
        }
    }



    private void drawNode(Graphics2D g2, Node node, int x, int y, int siblingGap, int levelGap) {
        if (node == null) {
            return; 
        }
        
        g2.setColor(Colors.getColorCustomSelection(node == activeNode, Color.WHITE, Color.YELLOW));

        g2.fillOval(x - gp.p(0.47), y - gp.p(0.51), gp.p(1.3), gp.p(1.2));
        g2.drawImage(gp.imageLoader.getIconForNode(node), x - gp.p(0.4), y - gp.p(0.4), gp.p(1.2), gp.p(1), null);

        // Berechne die Position der Kindknoten (nach oben verschoben)
        int childY = y - levelGap; // Nächste Ebene nach oben
        if (node.getLeft() != null) {
            g2.setColor(Colors.getColorCustomSelection(selectedIdx == 0 && node == activeNode, Color.WHITE, Color.ORANGE));
            int childX = x - siblingGap; // Links
            float[] dash = {10.0f}; // Definiert das Muster der gestrichelten Linie
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            g2.draw(new Line2D.Double(x, y, childX, childY));
            g2.setStroke(new BasicStroke(1)); // Setzt den Stroke zurück
            // Zeichne linken Kindknoten
            drawNode(g2, node.getLeft(), childX, childY, siblingGap / 2, levelGap);
            gp.drawLib.drawHover(g2, childX - gp.p(0.5), childY - gp.p(0.5), gp.p(1), gp.p(1), selectedIdx == 0 && node == activeNode);
        }
        if (node.getRight() != null) {
            g2.setColor(Colors.getColorCustomSelection(selectedIdx == 1 && node == activeNode, Color.WHITE, Color.ORANGE));
            int childX = x + siblingGap; // Rechts
            float[] dash = {10.0f}; // Definiert das Muster der gestrichelten Linie
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            g2.draw(new Line2D.Double(x, y, childX, childY));
            g2.setStroke(new BasicStroke(1)); // Setzt den Stroke zurück
            // Zeichne rechten Kindknoten
            drawNode(g2, node.getRight(), childX, childY, siblingGap / 2, levelGap);
            gp.drawLib.drawHover(g2, childX - gp.p(0.5), childY - gp.p(0.5), gp.p(1), gp.p(1), selectedIdx == 1 && node == activeNode);
        }
    }
}
