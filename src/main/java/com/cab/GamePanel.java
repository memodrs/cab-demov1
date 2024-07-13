package com.cab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.cab.card.CardLoader;
import com.cab.cardGame.CardGame;
import com.cab.network.Connection;

public class GamePanel extends JPanel implements Runnable {
    public boolean blockBtn;

    // SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	
	public int tileSize = originalTileSize * scale; // 48x48 tile
	
	public int cardHeight;
	public int cardWidth;
	public int selectedCardHeight;
	public int selectedCardWidth;

    // FPS
	int FPS = 60;

    // GAME STATE
	public int gameState;

    // States
	public final int titleState = 0;
	
	public final int hauptmenuState = 1;
	public final int cardMenuState = 2;
	public final int cardGameState = 3;

	public KeyHandler keyH = new KeyHandler(this);
	public Sound worldMusic = new Sound();
	public Sound soundEffect = new Sound();
	public ImageLoader imageLoader = new ImageLoader();
	public CardLoader cardLoader = new CardLoader();
    public Player player = new Player(this);

	public Connection connection;
    public Hauptmenu hauptmenu = new Hauptmenu(this);
    public CardMenu cardMenu = new CardMenu(this);
    public CardGame cardGame = new CardGame(this);

    Thread gameThread;

    public GamePanel() {
        cardWidth = (int) (tileSize * 2); 
		cardHeight = (int) (tileSize * 3);

		selectedCardWidth = cardWidth + 5;
		selectedCardHeight = cardHeight + 5;

        setTitleScreen();
		setPreferredSize(new Dimension(Main.screenWidth, Main.screenHeight));
		setBackground(Color.black);
		setDoubleBuffered(true);
		addKeyListener(keyH);
		setFocusable(true);
    }

    public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
    }

    @Override
    public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
			
			if (timer >= 1000000000) {
				timer = 0;
			}
		}
    }

    public void update() {
		hauptmenu.update();
		cardMenu.update();
		cardGame.update();
	}

    public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
	    
		if (gameState == hauptmenuState) {
			hauptmenu.draw(g2);
		} else if (gameState == cardMenuState) {
			cardMenu.draw(g2);
		} else if (gameState == cardGameState) {
			cardGame.draw(g2);
		} 
		
		g2.dispose();
	}

	public void setTitleScreen() {
		playMusic(9);
		gameState = hauptmenuState; //TODO irgendwann Title Screen
	}

    public void playSE(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
    }

    public void stopMuic() {
		worldMusic.stop();
    }

    public void playMusic(int i) {
		worldMusic.setFile(i);
		worldMusic.play();
		worldMusic.loop();
    }
    
}
