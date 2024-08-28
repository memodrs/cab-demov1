package com.cab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.cab.card.CardLoader;
import com.cab.cardGame.CardGame;
import com.cab.configs.Positions;
import com.cab.draw.ImageLoader;
import com.cab.draw.MenuInstraction;
import com.cab.network.Connection;
import com.cab.states.CardMenu;
import com.cab.states.CreateServer;
import com.cab.states.Hauptmenu;
import com.cab.states.JoinServer;
import com.cab.states.Language;
import com.cab.states.Shop;

public class GamePanel extends JPanel implements Runnable {
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
	public final int loadingState = 0;
	public final int languageState = 1;
	public final int titleState = 2;
	public final int hauptmenuState = 3;
	public final int cardMenuState = 4;
	public final int createServerState = 5;
	public final int joinServerState = 6;
	public final int cardGameState = 7;
	public final int shopState = 8;

	public Sound worldMusic = new Sound();
	public Sound soundEffect = new Sound();
	public ImageLoader imageLoader = new ImageLoader();
	public KeyHandler keyH = new KeyHandler();

	public CardLoader cardLoader;
    public Player player;
	public Connection connection;
	public Language language;
    public Hauptmenu hauptmenu;
	public CreateServer createServer;
	public JoinServer joinServer;
	public Shop shop;
    public CardMenu cardMenu;
    public CardGame cardGame;

	//Draw
	public MenuInstraction menuInstraction;

    Thread gameThread;

    public GamePanel() {
		setPreferredSize(new Dimension(Main.screenWidth, Main.screenHeight));
		setBackground(Color.black);
		setDoubleBuffered(true);
		setFocusable(true);
		setLoadingScreenState();
		addKeyListener(keyH);
    }

    public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();

		cardWidth = (int) (tileSize * 2); 
		cardHeight = (int) (tileSize * 3);

		selectedCardWidth = cardWidth + 5;
		selectedCardHeight = cardHeight + 5;

		imageLoader.init();

		cardLoader = new CardLoader();
		player = new Player(this);
		language = new Language(this);
		hauptmenu = new Hauptmenu(this);
		createServer = new CreateServer(this);
		joinServer = new JoinServer(this);
		
		shop = new Shop(this);
		cardMenu = new CardMenu(this);
		cardGame = new CardGame(this);

		menuInstraction = new MenuInstraction(this);

		playMusic(9);
		gameState = languageState; 
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
		if (gameState == languageState) {
			language.update();
		} else if (gameState == hauptmenuState) {
			hauptmenu.update();
		} else if (gameState == cardMenuState) {
			cardMenu.update();
		} else if (gameState == createServerState) {
			createServer.update();
		} else if (gameState == joinServerState) {
			joinServer.update();
		} else if (gameState == cardGameState) {
			cardGame.update();
		} else if (gameState == shopState) {
			shop.update();
		}
	}

    public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		if (gameState == loadingState) {
			g2.drawImage(imageLoader.loadingScreenBg, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
			g2.drawImage(imageLoader.loadingScreen.get(), 0, 0, Main.screenWidth, Main.screenHeight, null);
		} else if (gameState == languageState) {
			language.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == hauptmenuState) {
			hauptmenu.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == createServerState) {
			createServer.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == joinServerState) {
			joinServer.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == cardMenuState) {
			cardMenu.draw(g2);
		} else if (gameState == cardGameState) {
			cardGame.draw(g2);
		} else if (gameState == shopState) {
			shop.draw(g2);
			menuInstraction.draw(g2);
		}
		g2.dispose();
	}

	public void setLoadingScreenState() {
		playMusic(9);
		gameState = loadingState; 
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

	public Color getColorSelection(int target, int idx) {
		return idx == target? Color.YELLOW : Color.WHITE;
	}
    
}
