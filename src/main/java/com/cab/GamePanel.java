package com.cab;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import com.cab.card.CardLoader;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.configs.Sprache;
import com.cab.configs.Texte;
import com.cab.draw.ImageLoader;
import com.cab.draw.MenuInstraction;
import com.cab.network.Connection;
import com.cab.save.SaveManager;
import com.cab.states.CardMenu;
import com.cab.states.CreateServer;
import com.cab.states.FirstStart;
import com.cab.states.MainMenu;
import com.cab.states.JoinServer;
import com.cab.states.Language;
import com.cab.states.Lexicon;
import com.cab.states.Loading;
import com.cab.states.Option;
import com.cab.states.SaveGameCorrupt;
import com.cab.states.Shop;
import com.cab.states.GameState;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
	final int numberOfWidthTiles = 39;

    // FPS
	int FPS = 60;

    // GAME STATE
	private  GameState gameState;

	//Settings
	public Sprache selectedLanguage;
	public int soundLevel;
	public boolean showNavigationInstration = true;

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
	public final int savegameCorruptState = 12;

	public Texte texte = new Texte();
	public Sound worldMusic = new Sound();
	public Sound soundEffect = new Sound();
	public ImageLoader imageLoader = new ImageLoader();
	public KeyHandler keyH = new KeyHandler();
	public SaveManager saveManager = new SaveManager();

	public Loading loading;
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
	public FirstStart firstStart;
	public Option optionen;
	public SaveGameCorrupt savegameCorrupt;

	//Draw
	public MenuInstraction menuInstraction;

    Thread gameThread;

    public GamePanel() {
		loading = new Loading(this);
		
		setPreferredSize(new Dimension(Main.screenWidth, Main.screenHeight));
		setBackground(Color.black);
		setDoubleBuffered(true);
		setFocusable(true);
		switchState(loadingState);
		addKeyListener(keyH);
		hideCurser();
    }

    public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();

		imageLoader.init();

		firstStart = new FirstStart(this);
		cardLoader = new CardLoader(this);
		player = new Player();
		language = new Language(this);
		mainMenu = new MainMenu(this);
		createServer = new CreateServer(this);
		joinServer = new JoinServer(this);
		lexikon = new Lexicon(this);
		shop = new Shop(this);
		cardMenu = new CardMenu(this);
		cardGame = new CardGame(this);
		optionen = new Option(this);
		savegameCorrupt = new SaveGameCorrupt(this);

		//Draw
		menuInstraction = new MenuInstraction(this);

		if (saveManager.isSavegameExist()) {
			load();
		} else {
			soundLevel = 50;
			worldMusic.setVolume(soundLevel);
			soundEffect.setVolume(soundLevel);
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

	public void switchState(int gameStateId) {
		if (gameStateId == loadingState || gameStateId == cardMenuState) {
			showNavigationInstration = false;
		} else {
			showNavigationInstration = true;
		}
		
		if (gameStateId == loadingState) {
			gameState = loading;
		} else if (gameStateId == languageState) {
			gameState = language;
		} else if (gameStateId == firstState) {
			gameState = firstStart;
		} else if (gameStateId == mainMenuState) {
			gameState = mainMenu;
		} else if (gameStateId == cardMenuState) {
			gameState = cardMenu;
		} else if (gameStateId == createServerState) {
			gameState = createServer;
		} else if (gameStateId == joinServerState) {
			gameState = joinServer;
		} else if (gameStateId == lexikonState) {
			gameState = lexikon;
		} else if (gameStateId == cardGameState) {
			gameState = cardGame;
		} else if (gameStateId == shopState) {
			gameState = shop;
		} else if (gameStateId == optionState) {
			gameState = optionen;
		} else if (gameStateId == savegameCorruptState) {
			gameState = savegameCorrupt;
		}
	}

    public void update() {
		gameState.update();
	}

    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		gameState.draw(g2);
		if (showNavigationInstration) {
			menuInstraction.draw(g2);
		}

		g2.dispose();
	}

	private void hideCurser() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor invisibleCursor = toolkit.createCustomCursor(
			toolkit.getImage(""), new java.awt.Point(0, 0), "invisible"
		);
		this.setCursor(invisibleCursor); // Verstecke den Mauszeiger
	}

    public void playSE(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
		soundEffect.setVolume(soundLevel);
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
			worldMusic.setVolume(soundLevel);

		}
    }

	public void increaseSound() {
		soundLevel += 5;
        if (soundLevel > 100) {
            soundLevel = 100;
        }
        worldMusic.setVolume(soundLevel);
        soundEffect.setVolume(soundLevel);
	}

	public void decreaseSound() {
		soundLevel -= 5;
        if (soundLevel < 0) {
            soundLevel = 0;
        }
        worldMusic.setVolume(soundLevel);
        soundEffect.setVolume(soundLevel);
	}

	public void load() {
		saveManager.load(this);
	}

	public void save() {
		saveManager.save(player, selectedLanguage, soundLevel);
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
}
