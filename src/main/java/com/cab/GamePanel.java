package com.cab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.cab.card.Art;
import com.cab.card.CardLoader;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.configs.Positions;
import com.cab.configs.Sprache;
import com.cab.configs.Texte;
import com.cab.draw.ImageLoader;
import com.cab.draw.MenuInstraction;
import com.cab.network.Connection;
import com.cab.save.SaveManager;
import com.cab.singleplayer.BoardGame;
import com.cab.states.CardMenu;
import com.cab.states.CreateServer;
import com.cab.states.FirstStart;
import com.cab.states.MainMenu;
import com.cab.states.JoinServer;
import com.cab.states.Language;
import com.cab.states.Lexicon;
import com.cab.states.Option;
import com.cab.states.SaveGameCorrupt;
import com.cab.states.Shop;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
	final int numberOfWidthTiles = 39;

    // FPS
	int FPS = 60;

    // GAME STATE
	public int gameState;
	public Sprache selectedLanguage;

    // States
	public final int loadingState = 0;
	public final int languageState = 1;
	public final int titleState = 2;
	public final int mainMenuState = 3;
	public final int cardMenuState = 4;
	public final int createServerState = 5;
	public final int joinServerState = 6;
	public final int lexikonState = 7;
	public final int cardGameState = 8;
	public final int shopState = 9;
	public final int firstState = 10;
	public final int optionState = 11;
	public final int boardGameState = 12; 
	public final int savegameCorruptState = 13;

	public Texte texte = new Texte();
	public Sound worldMusic = new Sound();
	public Sound soundEffect = new Sound();
	public ImageLoader imageLoader = new ImageLoader();
	public KeyHandler keyH = new KeyHandler();
	public SaveManager saveManager = new SaveManager(this);

	
	public CardLoader cardLoader;
    public Player player;
	public Connection connection;
	public Language language;
    public MainMenu mainMenu;
	public CreateServer createServer;
	public JoinServer joinServer;
	public Lexicon lexikon;
	public Shop shop;
    public CardMenu cardMenu;
    public CardGame cardGame;
	public BoardGame boardGame;
	public FirstStart firstStart;
	public Option optionen;
	public SaveGameCorrupt savegameCorrupt;

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

		imageLoader.init();

		firstStart = new FirstStart(this);
		cardLoader = new CardLoader(this);
		player = new Player(this);
		language = new Language(this);
		mainMenu = new MainMenu(this);
		createServer = new CreateServer(this);
		joinServer = new JoinServer(this);
		lexikon = new Lexicon(this);
		shop = new Shop(this);
		cardMenu = new CardMenu(this);
		cardGame = new CardGame(this);
		boardGame = new BoardGame(this);
		optionen = new Option(this);
		savegameCorrupt = new SaveGameCorrupt(this);

		//Draw
		menuInstraction = new MenuInstraction(this);


		if (saveManager.isSavegameExist()) {
			saveManager.load();
			
			if (gameState != savegameCorruptState) {
				if (selectedLanguage == null) {
					selectedLanguage = Sprache.Englisch;
				} 
	
				if (player.stapel.size() < cardMenu.limitMaxStapel) {
					cardMenu.start();
				} else {
					mainMenu.start();
				}
			}
		} else {
			language.start();
		}
	}

	public void stop() {
		gameThread = null;
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
		} else if (gameState == firstState) {
			firstStart.update();
		} else if (gameState == mainMenuState) {
			mainMenu.update();
		} else if (gameState == cardMenuState) {
			cardMenu.update();
		} else if (gameState == createServerState) {
			createServer.update();
		} else if (gameState == joinServerState) {
			joinServer.update();
		} else if (gameState == lexikonState) {
			lexikon.update();
		} else if (gameState == cardGameState) {
			cardGame.update();
		} else if (gameState == shopState) {
			shop.update();
		} else if (gameState == optionState) {
			optionen.update();
		} else if (gameState == boardGameState) {
			boardGame.update();
		} else if (gameState == savegameCorruptState) {
			savegameCorrupt.update();
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
		} else if (gameState == firstState) {
			firstStart.draw(g2);
		} else if (gameState == mainMenuState) {
			mainMenu.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == createServerState) {
			createServer.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == joinServerState) {
			joinServer.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == lexikonState) {
			lexikon.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == cardMenuState) {
			cardMenu.draw(g2);
		} else if (gameState == cardGameState) {
			cardGame.draw(g2);
		} else if (gameState == shopState) {
			shop.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == optionState) {
			optionen.draw(g2);
			menuInstraction.draw(g2);
		} else if (gameState == boardGameState) {
			boardGame.draw(g2);
		} else if (gameState == savegameCorruptState) {
			savegameCorrupt.draw(g2);
		}
		g2.dispose();
	}

	public void setLoadingScreenState() {
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
		if (worldMusic.i != i) {
			worldMusic.stop();
			worldMusic.setFile(i);
			worldMusic.play();
			worldMusic.loop();
		}
    }

	public void load() {
		saveManager.load();
	}

	public void save() {
		saveManager.save();
	}

	public String t(String key) {
		if (selectedLanguage == Sprache.Deutsch) {
			if (texte.setDe.get(key) == null) {
				System.err.println(key + " nicht gefunden");
				return "";
			}
            return texte.setDe.get(key);
        } else if (selectedLanguage == Sprache.Englisch) {
			if (texte.setEng.get(key) == null) {
				System.err.println(key + " nicht gefunden");
				return "";
			}
            return texte.setEng.get(key);
        } else {
            return "";
        }
	}

	public String getStatusBeschreibung(Status status) {
		switch (status) {
			case Schild: 	return t("statusSchild");
			case Fluegel: 	return t("statusFluegel");
			case Gift: 		return t("statusGift");
			case Feuer: 	return t("statusFeuer");
			case Blitz: 	return t("statusBlitz");
			default: return "";
		}
	}

	public Color getColorSelection(int target, int idx) {
		return idx == target? Color.YELLOW : Color.WHITE;
	}

	public Color getColorForArt(Art art) {
		switch (art) {
			case Mensch: return Color.WHITE;
			case Tier: return Main.v.darkGreenColor;
			case Fabelwesen: return Main.v.gold;
			case Nachtgestalt: return Main.v.purpleColor;
			case Segen: return Color.YELLOW;
			case Fluch: return Color.BLACK;
			case Unbekannt: return Color.RED;
			default: return null;
		}
	}
    
}
